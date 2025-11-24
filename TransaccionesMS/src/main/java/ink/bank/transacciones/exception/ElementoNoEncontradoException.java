package ink.bank.transacciones.exception;

/** Excepción lanzada cuando un elemento no es encontrado. */
public class ElementoNoEncontradoException extends RuntimeException {

  public ElementoNoEncontradoException(String mensaje) {
    super(mensaje);
  }
}
