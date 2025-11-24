// java
package ink.bank.transacciones.exception;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ink.bank.transacciones.dto.ResultRes;
import ink.bank.transacciones.util.Constantes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ExtendWith(MockitoExtension.class)
class ManejadorGlobalExcepcionesTest {

  private final ManejadorGlobalExcepciones handler = new ManejadorGlobalExcepciones();

  @Test
  @DisplayName("ElementoNoEncontradoException")
  void manejarElementoNoEncontrado() {
    // Arrange
    ElementoNoEncontradoException ex = mock(ElementoNoEncontradoException.class);
    when(ex.getMessage()).thenReturn("Elemento inexistente");

    // Act
    ResponseEntity<ResultRes> response = handler.manejarRecursoNoEncontrado(ex);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(Constantes.Status.ERROR, response.getBody().getStatus());
    assertEquals("Elemento inexistente", response.getBody().getMessage());
  }

  @Test
  @DisplayName("NoResourceFoundException")
  void manejarRecursoNoEncontrado() {
    // Arrange
    NoResourceFoundException ex = mock(NoResourceFoundException.class);
    when(ex.getMessage()).thenReturn("ruta no encontrada");

    // Act
    ResponseEntity<ResultRes> response = handler.manejarRecursoNoEncontrado(ex);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(Constantes.Status.ERROR, response.getBody().getStatus());
    assertEquals("El servicio accedido no existe.", response.getBody().getMessage());
  }

  @Test
  @DisplayName("ErrorGeneralException")
  void manejarErrorGeneral() {
    // Arrange
    ErrorGeneralException ex = mock(ErrorGeneralException.class);
    when(ex.getMessage()).thenReturn("error general específico");

    // Act
    ResponseEntity<ResultRes> response = handler.manejarErrorGeneral(ex);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(Constantes.Status.ERROR, response.getBody().getStatus());
    assertEquals("error general específico", response.getBody().getMessage());
  }

  @Test
  @DisplayName("MethodArgumentTypeMismatchException")
  void manejarErrorTipoDatoIncorrecto() {
    // Arrange
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getValue()).thenReturn("abc");

    // Act
    ResponseEntity<ResultRes> response = handler.manejarErrorTipoDatoIncorrecto(ex);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(Constantes.Status.ERROR, response.getBody().getStatus());
    assertTrue(response.getBody().getMessage().contains("abc"));
  }

  @Test
  @DisplayName("IllegalArgumentException")
  void manejarIllegalArgument() {
    // Arrange
    IllegalArgumentException ex = new IllegalArgumentException("argumento inválido");

    // Act
    ResponseEntity<ResultRes> response = handler.manejarExcepcionGeneral(ex);

    // Assert
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(Constantes.Status.ERROR, response.getBody().getStatus());
    assertEquals("argumento inválido", response.getBody().getMessage());
  }

  @Test
  @DisplayName("Excepción general")
  void manejarExcepcionGeneral() {
    // Arrange
    Exception ex = new Exception("surprise");

    // Act
    ResponseEntity<ResultRes> response = handler.manejarExcepcionGeneral(ex);

    // Assert
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(Constantes.Status.ERROR, response.getBody().getStatus());
    assertEquals("Ocurrió un error inesperado", response.getBody().getMessage());
  }
}
