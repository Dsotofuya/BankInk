package ink.bank.prueba.service.implementation;

import ink.bank.prueba.dto.ResultRes;
import ink.bank.prueba.dto.activartarjeta.ActivarTarjetaReq;
import ink.bank.prueba.dto.consultarbalance.ConsultarBalanceRes;
import ink.bank.prueba.dto.generarnumerotarjeta.GeneracionTarjetaRes;
import ink.bank.prueba.dto.recargarbalance.RecargarBalanceReq;
import ink.bank.prueba.enums.EstadoTarjetaEnum;
import ink.bank.prueba.exception.ElementoNoEncontradoException;
import ink.bank.prueba.jpa.entity.ProductoEntity;
import ink.bank.prueba.jpa.entity.TarjetaEntity;
import ink.bank.prueba.jpa.repository.ProductoRepository;
import ink.bank.prueba.jpa.repository.TarjetaRepository;
import ink.bank.prueba.service.ITarjetaService;
import ink.bank.prueba.util.Constantes;
import ink.bank.prueba.util.ConversionUtilidad;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Implementación del servicio para la gestión de tarjetas. */
@Service
@RequiredArgsConstructor
@Slf4j
public class TarjetaService implements ITarjetaService {

  private final ProductoRepository productoRepository;

  private final TarjetaRepository tarjetaRepository;

  @Override
  public GeneracionTarjetaRes generarNumeroTarjeta(Long productId) {
    log.info("Inicio de la generación de número de tarjeta para el producto con ID: {}", productId);
    ProductoEntity producto =
        productoRepository
            .findById(productId)
            .orElseThrow(
                () ->
                    new ElementoNoEncontradoException(
                        "Producto no encontrado con ID: " + productId));

    log.info("Generando tarjeta para el producto con ID: {}", productId);
    String numeroTarjeta = obtenerPrimerosDigitos(producto.getId()) + generarNumeroAleatorio();

    TarjetaEntity tarjeta = new TarjetaEntity();
    tarjeta.setNumeroTarjeta(numeroTarjeta);
    tarjeta.setNombreTitular(producto.getPrimerNombre() + " " + producto.getPrimerApellido());
    tarjeta.setFechaVencimiento(sumarAnios(new Date(), 3));
    tarjeta.setEstado(EstadoTarjetaEnum.I);
    tarjeta.setDivisa(Constantes.USD);
    tarjeta.setBalance(new BigDecimal(0));

    tarjetaRepository.save(tarjeta);

    log.info("Número de tarjeta generado exitosamente: {}", numeroTarjeta);
    return new GeneracionTarjetaRes(
        Constantes.Status.OK, "Número de tarjeta generado exitosamente.", numeroTarjeta);
  }

  @Override
  public ResultRes activarTarjeta(ActivarTarjetaReq activarTarjetaReq) {
    log.info(
        "Inicio del proceso de activación para la tarjeta con número: {}",
        activarTarjetaReq.getCardId());
    TarjetaEntity tarjeta = consultarTarjeta(activarTarjetaReq.getCardId());

    if (tarjeta.getEstado() == EstadoTarjetaEnum.A) {
      log.warn("La tarjeta con número {} ya está activada.", activarTarjetaReq.getCardId());
      return new ResultRes(Constantes.Status.ERROR, "La tarjeta ya está activada.");
    }

    tarjeta.setEstado(EstadoTarjetaEnum.A);
    tarjetaRepository.save(tarjeta);

    return new ResultRes(Constantes.Status.OK, "Tarjeta activada exitosamente.");
  }

  @Override
  public ResultRes bloquearTarjeta(String cardId) {
    log.info("Inicio del proceso de bloqueo para la tarjeta con número: {}", cardId);

    TarjetaEntity tarjeta = consultarTarjeta(cardId);
    if (tarjeta.getEstado() == EstadoTarjetaEnum.B) {
      log.warn("La tarjeta con número {} ya está bloqueada.", cardId);
      return new ResultRes(Constantes.Status.ERROR, "La tarjeta ya está bloqueada.");
    }

    tarjeta.setEstado(EstadoTarjetaEnum.B);
    tarjetaRepository.save(tarjeta);

    return new ResultRes(Constantes.Status.OK, "Tarjeta bloqueada exitosamente.");
  }

  @Override
  public ResultRes recargarBalance(RecargarBalanceReq recargarBalanceReq) {
    log.info(
        "Inicio del proceso de recargar balance para la tarjeta con número: {}",
        recargarBalanceReq.getCardId());

    if (new BigDecimal(recargarBalanceReq.getBalance()).compareTo(BigDecimal.ZERO) <= 0) {
      log.warn("El monto a recargar debe ser mayor que cero.");
      return new ResultRes(Constantes.Status.ERROR, "El monto a recargar debe ser mayor que cero.");
    }

    TarjetaEntity tarjeta = consultarTarjeta(recargarBalanceReq.getCardId());
    tarjeta.setBalance(
        tarjeta
            .getBalance()
            .add(ConversionUtilidad.toCents(new BigDecimal(recargarBalanceReq.getBalance()))));
    tarjetaRepository.save(tarjeta);
    return new ResultRes(Constantes.Status.OK, "Balance recargado exitosamente.");
  }

  @Override
  public ConsultarBalanceRes consultarBalance(String cardId) {
    log.info("Inicio del proceso de consultar balance para la tarjeta con número: {}", cardId);
    TarjetaEntity tarjeta = consultarTarjeta(cardId);
    BigDecimal balanceEnDolares = ConversionUtilidad.calcularDolares(tarjeta.getBalance());

    return new ConsultarBalanceRes(
        Constantes.Status.OK, "Balance consultado exitosamente.", balanceEnDolares.toString());
  }

  /**
   * Consulta una tarjeta por su número.
   *
   * @param numeroTarjeta Número de la tarjeta.
   * @return TarjetaEntity encontrada.
   * @throws ElementoNoEncontradoException Si la tarjeta no es encontrada.
   */
  @Override
  public TarjetaEntity consultarTarjeta(String numeroTarjeta) {
    return tarjetaRepository
        .findByNumeroTarjeta(numeroTarjeta)
        .orElseThrow(
            () ->
                new ElementoNoEncontradoException(
                    "Tarjeta no encontrada con número: " + numeroTarjeta));
  }

  private String obtenerPrimerosDigitos(long value) {
    String s = Long.toString(value);
    if (s.length() >= 6) {
      return s.substring(0, 6);
    } else {
      return String.format("%06d", Long.parseLong(s));
    }
  }

  private String generarNumeroAleatorio() {
    long maxExclusive = 10_000_000_000L;
    long value = ThreadLocalRandom.current().nextLong(0, maxExclusive);
    return String.format("%010d", value);
  }

  public static Date sumarAnios(Date fecha, int anios) {
    Instant instant = fecha.toInstant();
    ZoneId zone = ZoneId.systemDefault();
    ZonedDateTime zdt = instant.atZone(zone).plusYears(anios);
    return Date.from(zdt.toInstant());
  }
}
