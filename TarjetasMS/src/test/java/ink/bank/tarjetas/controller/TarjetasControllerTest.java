package ink.bank.tarjetas.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ink.bank.tarjetas.dto.ResultRes;
import ink.bank.tarjetas.dto.activartarjeta.ActivarTarjetaReq;
import ink.bank.tarjetas.dto.consultarbalance.ConsultarBalanceRes;
import ink.bank.tarjetas.dto.generarnumerotarjeta.GeneracionTarjetaRes;
import ink.bank.tarjetas.dto.recargarbalance.RecargarBalanceReq;
import ink.bank.tarjetas.service.ITarjetaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class TarjetasControllerTest {

  public static final String CARD_NUMBER = "1234567890123456";

  @Mock private ITarjetaService tarjetaService;

  @InjectMocks private TarjetasController controller;

  @Test
  @DisplayName("Generar número de tarjeta - OK")
  void generarNumeroTarjetaOk() {
    // Arrange
    Long productId = 1L;
    GeneracionTarjetaRes expected = mock(GeneracionTarjetaRes.class);
    when(tarjetaService.generarNumeroTarjeta(productId)).thenReturn(expected);

    // Act
    ResponseEntity<GeneracionTarjetaRes> response = controller.generarNumeroTarjeta(productId);

    // Assert
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertSame(expected, response.getBody());
    verify(tarjetaService).generarNumeroTarjeta(productId);
  }

  @Test
  @DisplayName("Activar tarjeta - OK")
  void activarTarjetaOK() {
    // Arrange
    ActivarTarjetaReq req = new ActivarTarjetaReq();
    ResultRes expected = mock(ResultRes.class);
    when(tarjetaService.activarTarjeta(req)).thenReturn(expected);

    // Act
    ResponseEntity<ResultRes> response = controller.activarTarjeta(req);

    // Assert
    assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    assertSame(expected, response.getBody());
    verify(tarjetaService).activarTarjeta(req);
  }

  @Test
  @DisplayName("Bloquear tarjeta - OK")
  void bloquearTarjetaOk() {
    // Arrange
    String cardId = CARD_NUMBER;
    ResultRes expected = mock(ResultRes.class);
    when(tarjetaService.bloquearTarjeta(cardId)).thenReturn(expected);

    // Act
    ResponseEntity<ResultRes> response = controller.bloquearTarjeta(cardId);

    // Assert
    assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    assertSame(expected, response.getBody());
    verify(tarjetaService).bloquearTarjeta(cardId);
  }

  @Test
  @DisplayName("Recargar balance - OK")
  void recargarBalanceOk() {
    // Arrange
    RecargarBalanceReq req = new RecargarBalanceReq(CARD_NUMBER, "10");
    ResultRes expected = mock(ResultRes.class);
    when(tarjetaService.recargarBalance(req)).thenReturn(expected);

    // Act
    ResponseEntity<ResultRes> response = controller.recargarBalance(req);

    // Assert
    assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    assertSame(expected, response.getBody());
    verify(tarjetaService).recargarBalance(req);
  }

  @Test
  @DisplayName("Consultar balance - OK")
  void consultarBalanceOk() {
    // Arrange
    String cardId = CARD_NUMBER;
    ConsultarBalanceRes expected = mock(ConsultarBalanceRes.class);
    when(tarjetaService.consultarBalance(cardId)).thenReturn(expected);

    // Act
    ResponseEntity<ConsultarBalanceRes> response = controller.consultarBalance(cardId);

    // Assert
    assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    assertSame(expected, response.getBody());
    verify(tarjetaService).consultarBalance(cardId);
  }
}
