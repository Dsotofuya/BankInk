package ink.bank.prueba.dto.registrartransaccion;

import ink.bank.prueba.util.Constantes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Modelo de datos de entrada para registrar una transacción. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description = "Modelo de datos de entrada para registrar una transacción")
public class TransaccionReq {

  @NotBlank(message = Constantes.Msj.MSJ_CANT_EMPTY)
  @NotNull(message = Constantes.Msj.MSJ_NOT_NULL)
  @Schema(description = "Número de tarjeta", example = "1234567890123456")
  private String cardId;

  @NotBlank(message = Constantes.Msj.MSJ_CANT_EMPTY)
  @NotNull(message = Constantes.Msj.MSJ_NOT_NULL)
  @Schema(description = "Precio", example = "10")
  private String price;
}
