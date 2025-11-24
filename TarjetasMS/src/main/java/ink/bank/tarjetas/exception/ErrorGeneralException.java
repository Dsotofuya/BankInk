package ink.bank.tarjetas.exception;

/** Excepción general para errores en la aplicación. */
public class ErrorGeneralException extends RuntimeException {

  public ErrorGeneralException(String mensaje) {
    super(mensaje);
  }
}
