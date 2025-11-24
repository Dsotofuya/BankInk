package ink.bank.transacciones.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

/** Clase de utilidad para conversiones. */
public class ConversionUtilidad {

  private ConversionUtilidad() {
    throw new IllegalArgumentException("Clase de utilidad");
  }

  /**
   * Convierte una cantidad en dólares a centavos.
   *
   * @param amount Cantidad en dólares.
   * @return Cantidad en centavos.
   */
  public static BigDecimal toCents(BigDecimal amount) {
    return amount.multiply(new BigDecimal("100"));
  }

  /**
   * Convierte una cantidad en centavos a dólares.
   *
   * @param amount Cantidad en centavos.
   * @return Cantidad en dólares.
   */
  public static BigDecimal calcularDolares(BigDecimal amount) {
    return amount.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
  }

  /**
   * Calcula las horas entre la fecha de compra y el plazo de anulación (24 horas antes).
   *
   * @param fechaCompra Fecha de compra.
   * @return Número de horas entre la fecha de compra y el plazo de anulación.
   */
  public static Date calcularPlazoAnulacion(Date fechaCompra) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(fechaCompra);
    calendar.add(Calendar.HOUR_OF_DAY, +24);
    return calendar.getTime();
  }
}
