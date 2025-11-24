package ink.bank.tarjetas.exception;

import ink.bank.tarjetas.dto.ResultRes;
import ink.bank.tarjetas.util.Constantes;
import java.text.MessageFormat;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/** Manejador global de excepciones para la aplicación. */
@ControllerAdvice
@Slf4j
public class ManejadorGlobalExcepciones {

  @ExceptionHandler(ElementoNoEncontradoException.class)
  public ResponseEntity<ResultRes> manejarRecursoNoEncontrado(ElementoNoEncontradoException ex) {
    log.error("Error de elemento no encontrado: {}", ex.getMessage());
    return new ResponseEntity<>(
        new ResultRes(Constantes.Status.ERROR, ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ResultRes> manejarRecursoNoEncontrado(NoResourceFoundException ex) {
    log.error("Error de recurso no encontrado: {}", ex.getMessage());
    return new ResponseEntity<>(
        new ResultRes(Constantes.Status.ERROR, "El servicio accedido no existe."),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ErrorGeneralException.class)
  public ResponseEntity<ResultRes> manejarErrorGeneral(ErrorGeneralException ex) {
    log.error("Error general: {}", ex.getMessage());
    return new ResponseEntity<>(
        new ResultRes(Constantes.Status.ERROR, ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ResultRes> manejarErrorTipoDatoIncorrecto(
      MethodArgumentTypeMismatchException ex) {
    log.error("Error general: {}", ex.getMessage());
    return new ResponseEntity<>(
        new ResultRes(
            Constantes.Status.ERROR,
            MessageFormat.format(
                "El valor : {0} no es del tipo esperado", ex.getValue().toString())),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResultRes> manejarExcepcionGeneral(IllegalArgumentException ex) {
    log.error("Error de argumentos: {}", ex.getMessage());
    return new ResponseEntity<>(
        new ResultRes(Constantes.Status.ERROR, ex.getMessage()), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResultRes> manejarErrorValidacion(MethodArgumentNotValidException ex) {
    log.error("Error de validacion: {}", ex.getBody().getDetail());
    String message =
        ex.getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.joining(", "));

    return new ResponseEntity<>(
        new ResultRes(Constantes.Status.ERROR, message), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResultRes> manejarExcepcionGeneral(Exception ex) {
    log.error("Error inesperado: {}", ex.getMessage(), ex);
    return new ResponseEntity<>(
        new ResultRes(Constantes.Status.ERROR, "Ocurrió un error inesperado"),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
