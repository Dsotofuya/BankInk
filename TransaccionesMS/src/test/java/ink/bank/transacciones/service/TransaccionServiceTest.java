// java
package ink.bank.transacciones.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ink.bank.transacciones.dto.ResultRes;
import ink.bank.transacciones.dto.TransaccionDTO;
import ink.bank.transacciones.dto.anulartransaccion.AnulacionReq;
import ink.bank.transacciones.dto.obtenertransaccion.TransactionRes;
import ink.bank.transacciones.dto.registrartransaccion.TransaccionReq;
import ink.bank.transacciones.enums.EstadoTarjetaEnum;
import ink.bank.transacciones.exception.ElementoNoEncontradoException;
import ink.bank.transacciones.exception.ErrorGeneralException;
import ink.bank.transacciones.jpa.entity.TarjetaEntity;
import ink.bank.transacciones.jpa.entity.TransaccionEntity;
import ink.bank.transacciones.jpa.repository.TarjetaRepository;
import ink.bank.transacciones.jpa.repository.TransaccionRepository;
import ink.bank.transacciones.mapper.TransaccionMapper;
import ink.bank.transacciones.service.implementation.TransaccionService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransaccionServiceTest {

  public static final String CARD_NUMBER = "1234567890123456";
  public static final int DIA_MILIS = 86_400_000;
  @Mock private TransaccionRepository transaccionRepository;
  @Mock private TarjetaRepository tarjetaRepository;
  @Mock private TransaccionMapper transaccionMapper;

  @InjectMocks private TransaccionService service;

  @Test
  @DisplayName("Obtener transacción - OK")
  void obtenerTransaccionOk() {
    // Arrange
    Long id = 1L;
    TransaccionEntity entity = new TransaccionEntity();
    entity.setId(id);
    TransaccionDTO dto = new TransaccionDTO();
    when(transaccionRepository.findById(id)).thenReturn(Optional.of(entity));
    when(transaccionMapper.toDto(entity)).thenReturn(dto);

    // Act
    TransactionRes res = service.obtenerTransaccion(id);

    // Assert
    assertNotNull(res);
    assertEquals("Consulta exitosa", res.getMessage());
    verify(transaccionRepository).findById(id);
    verify(transaccionMapper).toDto(entity);
  }

  @Test
  @DisplayName("Obtener transacción - No encontrada")
  void obtenerTransaccionNoEncontrada() {
    // Arrange
    Long id = 2L;
    when(transaccionRepository.findById(id)).thenReturn(Optional.empty());

    // Act / Assert
    assertThrows(ElementoNoEncontradoException.class, () -> service.obtenerTransaccion(id));
    verify(transaccionRepository).findById(id);
  }

  @Test
  @DisplayName("Realizar transacción - OK")
  void realizarTransaccionOk() {
    // Arrange
    TransaccionReq req = new TransaccionReq(CARD_NUMBER, "10");

    TarjetaEntity tarjeta = new TarjetaEntity();
    tarjeta.setNumeroTarjeta("card-1");
    tarjeta.setFechaVencimiento(new Date(System.currentTimeMillis() + DIA_MILIS));
    tarjeta.setEstado(EstadoTarjetaEnum.A);
    tarjeta.setBalance(new BigDecimal("100000"));

    when(tarjetaRepository.findByNumeroTarjeta(anyString())).thenReturn(Optional.of(tarjeta));
    when(transaccionRepository.save(any(TransaccionEntity.class)))
        .thenAnswer(inv -> inv.getArgument(0));

    // Act
    ResultRes res = service.realizarTransaccion(req);

    // Assert
    assertNotNull(res);
    assertEquals("Transacción realizada con éxito.", res.getMessage());
    verify(tarjetaRepository).findByNumeroTarjeta(CARD_NUMBER);
    verify(tarjetaRepository).saveAndFlush(any(TarjetaEntity.class));
    verify(transaccionRepository).save(any(TransaccionEntity.class));
  }

  @Test
  @DisplayName("Realizar transacción - Tarjeta no encontrada")
  void realizarTransaccion() {
    // Arrange
    TransaccionReq req = new TransaccionReq(CARD_NUMBER, "5");
    when(tarjetaRepository.findByNumeroTarjeta(anyString())).thenReturn(Optional.empty());

    // Act / Assert
    assertThrows(ElementoNoEncontradoException.class, () -> service.realizarTransaccion(req));
    verify(tarjetaRepository).findByNumeroTarjeta(CARD_NUMBER);
  }

  @Test
  @DisplayName("Realizar transacción - Tarjeta vencida")
  void realizarTransaccionTarjetaVencida() {
    // Arrange
    TransaccionReq req = new TransaccionReq(CARD_NUMBER, "1");
    TarjetaEntity tarjeta = new TarjetaEntity();
    tarjeta.setNumeroTarjeta(CARD_NUMBER);
    tarjeta.setFechaVencimiento(new Date(System.currentTimeMillis() - DIA_MILIS));
    tarjeta.setEstado(EstadoTarjetaEnum.A);
    tarjeta.setBalance(new BigDecimal("100000"));
    when(tarjetaRepository.findByNumeroTarjeta(CARD_NUMBER)).thenReturn(Optional.of(tarjeta));

    // Act / Assert
    assertThrows(ErrorGeneralException.class, () -> service.realizarTransaccion(req));
    verify(tarjetaRepository).findByNumeroTarjeta(CARD_NUMBER);
  }

  @Test
  @DisplayName("Realizar transacción - Fondos insuficientes")
  void realizarTransaccionFondosInsuficientes() {
    // Arrange
    TransaccionReq req = new TransaccionReq(CARD_NUMBER, "1000000");
    TarjetaEntity tarjeta = new TarjetaEntity();
    tarjeta.setNumeroTarjeta(CARD_NUMBER);
    tarjeta.setFechaVencimiento(new Date(System.currentTimeMillis() + DIA_MILIS));
    tarjeta.setEstado(EstadoTarjetaEnum.A);
    tarjeta.setBalance(new BigDecimal("1"));
    when(tarjetaRepository.findByNumeroTarjeta(CARD_NUMBER)).thenReturn(Optional.of(tarjeta));

    // Act / Assert
    assertThrows(ErrorGeneralException.class, () -> service.realizarTransaccion(req));
    verify(tarjetaRepository).findByNumeroTarjeta(CARD_NUMBER);
  }

  @Test
  @DisplayName("Anular transacción - OK")
  void anularTransaccionOk() {
    // Arrange
    AnulacionReq req = new AnulacionReq();
    req.setTransactionId("10");
    req.setCardId(CARD_NUMBER);

    TarjetaEntity tarjeta = new TarjetaEntity();
    tarjeta.setNumeroTarjeta(CARD_NUMBER);
    tarjeta.setBalance(new BigDecimal("5000"));

    TransaccionEntity transaccion = new TransaccionEntity();
    transaccion.setId(10L);
    transaccion.setFechaTransaccion(
        new Date(System.currentTimeMillis() - 3_600_000)); // 1 hora atrás
    transaccion.setMontoTransaccion(new BigDecimal("1000"));
    transaccion.setTarjeta(tarjeta);

    when(transaccionRepository.findByIdAndTarjetaNumeroTarjeta(10L, CARD_NUMBER))
        .thenReturn(Optional.of(transaccion));
    when(transaccionRepository.save(any(TransaccionEntity.class)))
        .thenAnswer(i -> i.getArgument(0));
    when(tarjetaRepository.save(any(TarjetaEntity.class))).thenAnswer(i -> i.getArgument(0));

    // Act
    ResultRes res = service.anularTransaccion(req);

    // Assert
    assertNotNull(res);
    assertEquals("Transacción anulada con éxito.", res.getMessage());
    verify(transaccionRepository).findByIdAndTarjetaNumeroTarjeta(10L, CARD_NUMBER);
    verify(tarjetaRepository).save(any(TarjetaEntity.class));
    verify(transaccionRepository).save(any(TransaccionEntity.class));
  }

  @Test
  @DisplayName("Anular transacción - No encontrada")
  void anularTransaccionNoEncontrada() {
    // Arrange
    AnulacionReq req = new AnulacionReq();
    req.setTransactionId("11");
    req.setCardId(CARD_NUMBER);
    when(transaccionRepository.findByIdAndTarjetaNumeroTarjeta(11L, CARD_NUMBER))
        .thenReturn(Optional.empty());

    // Act / Assert
    assertThrows(ElementoNoEncontradoException.class, () -> service.anularTransaccion(req));
    verify(transaccionRepository).findByIdAndTarjetaNumeroTarjeta(11L, CARD_NUMBER);
  }

  @Test
  @DisplayName("Anular transacción - No elegible para anulación Fecha")
  void anularTransaccionFechaAnulacionPasada() {
    // Arrange
    AnulacionReq req = new AnulacionReq();
    req.setTransactionId("12");
    req.setCardId(CARD_NUMBER);

    TarjetaEntity tarjeta = new TarjetaEntity();
    tarjeta.setNumeroTarjeta(CARD_NUMBER);
    tarjeta.setBalance(new BigDecimal("2000"));

    TransaccionEntity transaccion = new TransaccionEntity();
    transaccion.setId(12L);
    transaccion.setFechaTransaccion(new Date(System.currentTimeMillis() - 2L * DIA_MILIS));
    transaccion.setMontoTransaccion(new BigDecimal("500"));
    transaccion.setTarjeta(tarjeta);

    when(transaccionRepository.findByIdAndTarjetaNumeroTarjeta(12L, CARD_NUMBER))
        .thenReturn(Optional.of(transaccion));

    // Act / Assert
    assertThrows(ErrorGeneralException.class, () -> service.anularTransaccion(req));
    verify(transaccionRepository).findByIdAndTarjetaNumeroTarjeta(12L, CARD_NUMBER);
  }
}
