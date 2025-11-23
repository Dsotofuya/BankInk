package ink.bank.prueba.dto.recargarbalance;

import ink.bank.prueba.util.Constantes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** Modelo de datos de entrada para recargar balance. */
@AllArgsConstructor
@Getter
@Schema(description = "Modelo de datos de entrada para recargar balance")
public class RecargarBalanceReq {

  @NotBlank(message = Constantes.Msj.MSJ_CANT_EMPTY)
  @NotNull(message = Constantes.Msj.MSJ_NOT_NULL)
  @Schema(description = "Número de tarjeta", example = "1234567890123456")
  private String cardId;

  @NotNull(message = Constantes.Msj.MSJ_NOT_NULL)
  @NotBlank(message = Constantes.Msj.MSJ_CANT_EMPTY)
  @Schema(description = "Balance", example = "99.00")
  private String balance;
}
