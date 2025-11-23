package ink.bank.prueba.dto.generarnumerotarjeta;

import ink.bank.prueba.dto.ResultRes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/** DTO para la respuesta de generación de tarjeta. */
@Getter
@Setter
@Schema(description = "DTO para la respuesta de generación de tarjeta.")
public class GeneracionTarjetaRes extends ResultRes {

  @Schema(description = "Número de tarjeta", example = "1234567890123456")
  private String numeroTarjeta;

  @Builder
  public GeneracionTarjetaRes(String status, String message, String numeroTarjeta) {
    super(status, message);
    this.numeroTarjeta = numeroTarjeta;
  }
}
