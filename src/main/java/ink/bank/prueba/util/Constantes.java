package ink.bank.prueba.util;

/** Clase para definir constantes utilizadas en la aplicación. */
public class Constantes {

  private Constantes() {
    throw new IllegalStateException("Clase de constantes");
  }

  /* Constante para la moneda USD */
  public static final String USD = "USD";

  /* Clase para definir los posibles status */
  public class Status {

    private Status() {
      throw new IllegalStateException("Clase de constantes");
    }

    /* Ok */
    public static final String OK = "OK";

    /* Error */
    public static final String ERROR = "Error";
  }

  /** Clase de mensajes */
  public class Msj {

    private Msj() {
      throw new IllegalStateException("Clase de mensajes");
    }

    /* Mensaje campo no debe ser nulo */
    public static final String MSJ_NOT_NULL = "No puede ser nulo";

    /* Mensaje campo debe ser positivo */
    public static final String MSJ_GREATER_0 = "Debe ser mayor a 0";

    /* Mensaje campo no debe estar vacio */
    public static final String MSJ_CANT_EMPTY = "No puede estar vacío";
  }
}
