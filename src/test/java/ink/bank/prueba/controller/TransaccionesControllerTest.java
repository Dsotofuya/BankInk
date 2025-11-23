// java
package ink.bank.prueba.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ink.bank.prueba.dto.ResultRes;
import ink.bank.prueba.dto.anulartransaccion.AnulacionReq;
import ink.bank.prueba.dto.obtenertransaccion.TransactionRes;
import ink.bank.prueba.dto.registrartransaccion.TransaccionReq;
import ink.bank.prueba.service.ITransaccionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class TransaccionesControllerTest {

  @Mock private ITransaccionService transaccionService;

  @InjectMocks private TransaccionesController controller;

  @Test
  @DisplayName("Obtener transacción - OK")
  void obtenerTransaccionOk() {
    // Arrange
    Long transactionId = 1L;
    TransactionRes expected = mock(TransactionRes.class);
    when(transaccionService.obtenerTransaccion(transactionId)).thenReturn(expected);

    // Act
    ResponseEntity<TransactionRes> response = controller.obtenerTransaccion(transactionId);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertSame(expected, response.getBody());
    verify(transaccionService).obtenerTransaccion(transactionId);
  }

  @Test
  @DisplayName("Realizar transacción - OK")
  void realizarTransaccionOk() {
    // Arrange
    TransaccionReq req = new TransaccionReq();
    ResultRes expected = mock(ResultRes.class);
    when(transaccionService.realizarTransaccion(req)).thenReturn(expected);

    // Act
    ResponseEntity<ResultRes> response = controller.realizarTransaccion(req);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertSame(expected, response.getBody());
    verify(transaccionService).realizarTransaccion(req);
  }

  @Test
  @DisplayName("Anular transacción - OK")
  void anularTransaccionOk() {
    // Arrange
    AnulacionReq req = new AnulacionReq();
    ResultRes expected = mock(ResultRes.class);
    when(transaccionService.anularTransaccion(req)).thenReturn(expected);

    // Act
    ResponseEntity<ResultRes> response = controller.anularTransaccion(req);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertSame(expected, response.getBody());
    verify(transaccionService).anularTransaccion(req);
  }
}
