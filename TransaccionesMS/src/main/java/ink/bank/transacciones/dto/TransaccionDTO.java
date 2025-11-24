package ink.bank.transacciones.dto;

import ink.bank.transacciones.enums.EstadoTransaccionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** DTO para transaccion. */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "DTO para transaccion.")
public class TransaccionDTO {

  @Schema(description = "IdTransacción", example = "12345")
  private Long id;

  @Schema(description = "Monto transacción", example = "10.00")
  private String montoTransaccion;

  @Schema(
      description = "Fecha en que se realizó la transacción",
      example = "2025-11-23T07:09:17.659Z")
  private Date fechaTransaccion;

  @Schema(description = "Divisa de la transacción", example = "USD")
  private String divisaTransaccion;

  @Schema(description = "Estado de la transacción", example = "E")
  private EstadoTransaccionEnum estadoTransaccion;

  @Schema(description = "Número de tarjeta asociada a la transacción", example = "1234567890123456")
  private String numeroTarjeta;
}
