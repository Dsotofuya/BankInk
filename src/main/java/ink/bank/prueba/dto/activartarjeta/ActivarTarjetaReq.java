package ink.bank.prueba.dto.activartarjeta;

import ink.bank.prueba.util.Constantes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/** DTO para la solicitud de activación de tarjeta. */
@Getter
@Setter
@Schema(description = "DTO para la solicitud de activación de tarjeta")
public class ActivarTarjetaReq {

  @NotNull(message = Constantes.Msj.MSJ_NOT_NULL)
  @NotBlank(message = Constantes.Msj.MSJ_CANT_EMPTY)
  @Schema(description = "Número de tarjeta", example = "1234567890123456")
  private String cardId;
}
