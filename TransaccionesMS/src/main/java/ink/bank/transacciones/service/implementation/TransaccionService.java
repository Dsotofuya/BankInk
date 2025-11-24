package ink.bank.transacciones.service.implementation;

import ink.bank.transacciones.dto.ResultRes;
import ink.bank.transacciones.dto.TransaccionDTO;
import ink.bank.transacciones.dto.anulartransaccion.AnulacionReq;
import ink.bank.transacciones.dto.obtenertransaccion.TransactionRes;
import ink.bank.transacciones.dto.registrartransaccion.TransaccionReq;
import ink.bank.transacciones.enums.EstadoTarjetaEnum;
import ink.bank.transacciones.enums.EstadoTransaccionEnum;
import ink.bank.transacciones.exception.ElementoNoEncontradoException;
import ink.bank.transacciones.exception.ErrorGeneralException;
import ink.bank.transacciones.jpa.entity.TarjetaEntity;
import ink.bank.transacciones.jpa.entity.TransaccionEntity;
import ink.bank.transacciones.jpa.repository.TarjetaRepository;
import ink.bank.transacciones.jpa.repository.TransaccionRepository;
import ink.bank.transacciones.mapper.TransaccionMapper;
import ink.bank.transacciones.service.ITransaccionService;
import ink.bank.transacciones.util.Constantes;
import ink.bank.transacciones.util.ConversionUtilidad;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Servicio para manejar transacciones. */
@Service
@Slf4j
@RequiredArgsConstructor
public class TransaccionService implements ITransaccionService {

  private final TransaccionRepository transaccionRepository;

  private final TarjetaRepository tarjetaRepository;

  private final TransaccionMapper transaccionMapper;

  @Override
  public TransactionRes obtenerTransaccion(Long transactionId) {

    TransaccionEntity transaccion =
        transaccionRepository
            .findById(transactionId)
            .orElseThrow(
                () ->
                    new ElementoNoEncontradoException(
                        "Transacción no encontrada con ID: " + transactionId));

    TransaccionDTO transaccionDTO = transaccionMapper.toDto(transaccion);

    return new TransactionRes(Constantes.Status.OK, "Consulta exitosa", transaccionDTO);
  }

  @Override
  @Transactional(rollbackOn = {Exception.class})
  public ResultRes realizarTransaccion(TransaccionReq transaccionReq) {
    log.info("Iniciando transacción para la tarjeta con ID: {}", transaccionReq.getCardId());
    TarjetaEntity tarjeta =
        tarjetaRepository
            .findByNumeroTarjeta(transaccionReq.getCardId())
            .orElseThrow(
                () ->
                    new ElementoNoEncontradoException(
                        "Tarjeta no encontrada con número: " + transaccionReq.getCardId()));

    if (tarjeta.getFechaVencimiento().before(new Date())) {
      throw new ErrorGeneralException("La tarjeta ha expirado.");
    }

    if (tarjeta.getEstado().equals(EstadoTarjetaEnum.I)) {
      throw new ErrorGeneralException("La tarjeta no se encuentra activa.");
    }

    if (tarjeta.getEstado().equals(EstadoTarjetaEnum.B)) {
      throw new ErrorGeneralException("La tarjeta se encuentra bloqueada.");
    }

    BigDecimal nuevoBalance =
        tarjeta
            .getBalance()
            .subtract(ConversionUtilidad.toCents(new BigDecimal(transaccionReq.getPrice())));
    if (nuevoBalance.compareTo(BigDecimal.ZERO) < 0) {
      throw new ErrorGeneralException("Fondos insuficientes en la tarjeta.");
    }
    tarjeta.setBalance(nuevoBalance);
    tarjetaRepository.saveAndFlush(tarjeta);

    TransaccionEntity transaccion = new TransaccionEntity();
    transaccion.setDivisaTransaccion(Constantes.USD);
    transaccion.setFechaTransaccion(new Date());
    transaccion.setMontoTransaccion(
        ConversionUtilidad.toCents(new BigDecimal(transaccionReq.getPrice())));
    transaccion.setTarjeta(tarjeta);
    transaccion.setEstadoTransaccion(EstadoTransaccionEnum.E);

    transaccionRepository.save(transaccion);

    return new ResultRes(Constantes.Status.OK, "Transacción realizada con éxito.");
  }

  @Override
  public ResultRes anularTransaccion(AnulacionReq anulacionReq) {
    log.info("Iniciando anulación de transacción con ID: {}", anulacionReq.getTransactionId());
    Date fechaActual = new Date();
    TransaccionEntity transaccion =
        transaccionRepository
            .findByIdAndTarjetaNumeroTarjeta(
                Long.valueOf(anulacionReq.getTransactionId()), anulacionReq.getCardId())
            .orElseThrow(
                () ->
                    new ElementoNoEncontradoException(
                        "Transacción no encontrada con ID: "
                            + anulacionReq.getTransactionId()
                            + " y Tarjeta: "
                            + anulacionReq.getCardId()));

    if (!fechaActual.after(transaccion.getFechaTransaccion())
        || !fechaActual.before(
            ConversionUtilidad.calcularPlazoAnulacion(transaccion.getFechaTransaccion()))) {
      log.info(
          "La transacción con ID: {} no es elegible para anulación.",
          anulacionReq.getTransactionId());
      throw new ErrorGeneralException("Solo se pueden anular transacciones de hasta 24 horas.");
    }

    transaccion.setEstadoTransaccion(EstadoTransaccionEnum.A);
    transaccion
        .getTarjeta()
        .setBalance(transaccion.getTarjeta().getBalance().add(transaccion.getMontoTransaccion()));

    tarjetaRepository.save(transaccion.getTarjeta());
    transaccionRepository.save(transaccion);

    return new ResultRes(Constantes.Status.OK, "Transacción anulada con éxito.");
  }
}
