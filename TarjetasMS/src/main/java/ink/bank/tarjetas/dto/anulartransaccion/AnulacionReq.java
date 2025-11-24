package ink.bank.tarjetas.dto.anulartransaccion;

import ink.bank.tarjetas.util.Constantes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/** DTO para la solicitud de anulación de transacción. */
@Getter
@Setter
@Schema(
    description = "DTO para la solicitud de anulación de transacción.",
    example = "1234567890123456")
public class AnulacionReq {

  @NotNull(message = Constantes.Msj.MSJ_NOT_NULL)
  @NotBlank(message = Constantes.Msj.MSJ_CANT_EMPTY)
  @Schema(description = "Número de tarjeta", example = "1234567890123456")
  private String cardId;

  @NotNull(message = Constantes.Msj.MSJ_NOT_NULL)
  @NotBlank(message = Constantes.Msj.MSJ_CANT_EMPTY)
  @Schema(description = "Número de transacción", example = "1234567")
  private String transactionId;
}
