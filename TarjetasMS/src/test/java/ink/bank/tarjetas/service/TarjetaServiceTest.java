// java
package ink.bank.tarjetas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ink.bank.tarjetas.dto.ResultRes;
import ink.bank.tarjetas.dto.activartarjeta.ActivarTarjetaReq;
import ink.bank.tarjetas.dto.consultarbalance.ConsultarBalanceRes;
import ink.bank.tarjetas.dto.generarnumerotarjeta.GeneracionTarjetaRes;
import ink.bank.tarjetas.dto.recargarbalance.RecargarBalanceReq;
import ink.bank.tarjetas.enums.EstadoTarjetaEnum;
import ink.bank.tarjetas.exception.ElementoNoEncontradoException;
import ink.bank.tarjetas.jpa.entity.ProductoEntity;
import ink.bank.tarjetas.jpa.entity.TarjetaEntity;
import ink.bank.tarjetas.jpa.repository.ProductoRepository;
import ink.bank.tarjetas.jpa.repository.TarjetaRepository;
import ink.bank.tarjetas.service.implementation.TarjetaService;
import ink.bank.tarjetas.util.Constantes;
import ink.bank.tarjetas.util.ConversionUtilidad;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TarjetaServiceTest {

  public static final String CARD_NUMBER = "1234567890123456";

  @Mock private ProductoRepository productoRepository;
  @Mock private TarjetaRepository tarjetaRepository;

  @InjectMocks private TarjetaService service;

  @Test
  @DisplayName("Generar número de tarjeta - OK")
  void generarNumeroTarjetaOk() {
    Long productId = 1L;
    ProductoEntity producto = new ProductoEntity();
    producto.setId(productId);
    producto.setPrimerNombre("Juan");
    producto.setPrimerApellido("Perez");
    when(productoRepository.findById(productId)).thenReturn(Optional.of(producto));
    when(tarjetaRepository.save(any(TarjetaEntity.class))).thenAnswer(i -> i.getArgument(0));

    GeneracionTarjetaRes res = service.generarNumeroTarjeta(productId);

    assertNotNull(res);
    assertEquals("Número de tarjeta generado exitosamente.", res.getMessage());
    assertEquals(Constantes.Status.OK, res.getStatus());
    assertNotNull(res.getNumeroTarjeta());
    assertEquals(16, res.getNumeroTarjeta().length());
    verify(productoRepository).findById(productId);
    verify(tarjetaRepository).save(any(TarjetaEntity.class));
  }

  @Test
  @DisplayName("Generar número de tarjeta - Producto no encontrado")
  void generarNumeroTarjetaProductoNoEncontrado() {
    Long productId = 2L;
    when(productoRepository.findById(productId)).thenReturn(Optional.empty());

    assertThrows(
        ElementoNoEncontradoException.class, () -> service.generarNumeroTarjeta(productId));
    verify(productoRepository).findById(productId);
    verifyNoInteractions(tarjetaRepository);
  }

  @Test
  @DisplayName("Activar tarjeta - OK")
  void activarTarjetaOk() {
    String cardId = CARD_NUMBER;
    TarjetaEntity tarjeta = new TarjetaEntity();
    tarjeta.setNumeroTarjeta(cardId);
    tarjeta.setEstado(EstadoTarjetaEnum.I);
    when(tarjetaRepository.findByNumeroTarjeta(cardId)).thenReturn(Optional.of(tarjeta));
    when(tarjetaRepository.save(any(TarjetaEntity.class))).thenAnswer(i -> i.getArgument(0));

    ActivarTarjetaReq req = new ActivarTarjetaReq();
    req.setCardId(cardId);

    ResultRes res = service.activarTarjeta(req);

    assertNotNull(res);
    assertEquals("Tarjeta activada exitosamente.", res.getMessage());
    assertEquals(Constantes.Status.OK, res.getStatus());
    verify(tarjetaRepository).findByNumeroTarjeta(cardId);
    verify(tarjetaRepository).save(any(TarjetaEntity.class));
    assertEquals(EstadoTarjetaEnum.A, tarjeta.getEstado());
  }

  @Test
  @DisplayName("Activar tarjeta inactiva - Tarjeta ya activada")
  void activarTarjetaYaActiva() {
    String cardId = CARD_NUMBER;
    TarjetaEntity tarjeta = new TarjetaEntity();
    tarjeta.setNumeroTarjeta(cardId);
    tarjeta.setEstado(EstadoTarjetaEnum.A);
    when(tarjetaRepository.findByNumeroTarjeta(cardId)).thenReturn(Optional.of(tarjeta));

    ActivarTarjetaReq req = new ActivarTarjetaReq();
    req.setCardId(cardId);

    ResultRes res = service.activarTarjeta(req);

    assertNotNull(res);
    assertEquals("La tarjeta ya está activada.", res.getMessage());
    assertEquals(Constantes.Status.ERROR, res.getStatus());
    verify(tarjetaRepository).findByNumeroTarjeta(cardId);
    verify(tarjetaRepository, never()).save(any());
  }

  @Test
  @DisplayName("Activar tarjeta - OK")
  void bloquearTarjetaOk() {
    String cardId = CARD_NUMBER;
    TarjetaEntity tarjeta = new TarjetaEntity();
    tarjeta.setNumeroTarjeta(cardId);
    tarjeta.setEstado(EstadoTarjetaEnum.A);
    when(tarjetaRepository.findByNumeroTarjeta(cardId)).thenReturn(Optional.of(tarjeta));
    when(tarjetaRepository.save(any(TarjetaEntity.class))).thenAnswer(i -> i.getArgument(0));

    ResultRes res = service.bloquearTarjeta(cardId);

    assertNotNull(res);
    assertEquals("Tarjeta bloqueada exitosamente.", res.getMessage());
    assertEquals(Constantes.Status.OK, res.getStatus());
    verify(tarjetaRepository).findByNumeroTarjeta(cardId);
    verify(tarjetaRepository).save(any(TarjetaEntity.class));
    assertEquals(EstadoTarjetaEnum.B, tarjeta.getEstado());
  }

  @Test
  @DisplayName("Bloquear tarjeta - Tarjeta ya bloqueada")
  void bloquearTarjetaBloqueada() {
    String cardId = CARD_NUMBER;
    TarjetaEntity tarjeta = new TarjetaEntity();
    tarjeta.setNumeroTarjeta(cardId);
    tarjeta.setEstado(EstadoTarjetaEnum.B);
    when(tarjetaRepository.findByNumeroTarjeta(cardId)).thenReturn(Optional.of(tarjeta));

    ResultRes res = service.bloquearTarjeta(cardId);

    assertNotNull(res);
    assertEquals("La tarjeta ya está bloqueada.", res.getMessage());
    assertEquals(Constantes.Status.ERROR, res.getStatus());
    verify(tarjetaRepository).findByNumeroTarjeta(cardId);
    verify(tarjetaRepository, never()).save(any());
  }

  @Test
  @DisplayName("Recargar balance - Monto inválido")
  void recargarBalanceMontoInvalido() {
    RecargarBalanceReq req = new RecargarBalanceReq(CARD_NUMBER, "-5");

    ResultRes res = service.recargarBalance(req);

    assertNotNull(res);
    assertEquals("El monto a recargar debe ser mayor que cero.", res.getMessage());
    assertEquals(Constantes.Status.ERROR, res.getStatus());
    verifyNoInteractions(tarjetaRepository);
  }

  @Test
  @DisplayName("Recargar balance - OK")
  void recargarBalanceOk() {
    RecargarBalanceReq req = new RecargarBalanceReq(CARD_NUMBER, "10");

    TarjetaEntity tarjeta = new TarjetaEntity();
    tarjeta.setNumeroTarjeta(CARD_NUMBER);
    tarjeta.setBalance(BigDecimal.ZERO);

    when(tarjetaRepository.findByNumeroTarjeta(CARD_NUMBER)).thenReturn(Optional.of(tarjeta));
    when(tarjetaRepository.save(any(TarjetaEntity.class))).thenAnswer(i -> i.getArgument(0));

    ResultRes res = service.recargarBalance(req);

    assertNotNull(res);
    assertEquals("Balance recargado exitosamente.", res.getMessage());
    assertEquals(Constantes.Status.OK, res.getStatus());
    verify(tarjetaRepository).findByNumeroTarjeta(CARD_NUMBER);
    verify(tarjetaRepository).save(any(TarjetaEntity.class));
    assertEquals(ConversionUtilidad.toCents(new BigDecimal("10")), tarjeta.getBalance());
  }

  @Test
  @DisplayName("Consultar balance - OK")
  void consultarBalanceOk() {
    String cardId = CARD_NUMBER;
    TarjetaEntity tarjeta = new TarjetaEntity();
    tarjeta.setNumeroTarjeta(cardId);
    tarjeta.setBalance(new BigDecimal("1500"));
    when(tarjetaRepository.findByNumeroTarjeta(cardId)).thenReturn(Optional.of(tarjeta));

    ConsultarBalanceRes res = service.consultarBalance(cardId);

    assertNotNull(res);
    assertEquals("Balance consultado exitosamente.", res.getMessage());
    assertEquals(Constantes.Status.OK, res.getStatus());
    String esperado = ConversionUtilidad.calcularDolares(tarjeta.getBalance()).toString();
    assertEquals(esperado, res.getBalance());
    verify(tarjetaRepository).findByNumeroTarjeta(cardId);
  }

  @Test
  @DisplayName("Consultar tarjeta - No encontrada")
  void consultarTarjetaNoEncontrada() {
    String cardId = CARD_NUMBER;
    when(tarjetaRepository.findByNumeroTarjeta(cardId)).thenReturn(Optional.empty());

    assertThrows(ElementoNoEncontradoException.class, () -> service.consultarTarjeta(cardId));
    verify(tarjetaRepository).findByNumeroTarjeta(cardId);
  }
}
