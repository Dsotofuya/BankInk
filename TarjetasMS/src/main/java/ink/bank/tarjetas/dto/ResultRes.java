package ink.bank.tarjetas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** Modelo de datos de salida para el resultado. */
@Getter
@AllArgsConstructor
@Schema(description = "Modelo de datos de salida para el resultado")
public class ResultRes {

  @Schema(description = "Status de la invocación", example = "OK")
  private String status;

  @Schema(
      description = "Mensaje con el resultado de la invocación",
      example = "Consulta o transacción exitosa")
  private String message;
}
