package ink.bank.prueba.dto.obtenertransaccion;

import ink.bank.prueba.dto.ResultRes;
import ink.bank.prueba.dto.TransaccionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/** DTO para la respuesta de la consulta de una transacción. */
@Getter
@Setter
@Schema(description = "DTO para la respuesta de la consulta de una transacción")
public class TransactionRes extends ResultRes {

  @Schema(description = "Objeto de transaccion")
  private TransaccionDTO transaccion;

  @Builder
  public TransactionRes(String status, String message, TransaccionDTO transaccion) {
    super(status, message);
    this.transaccion = transaccion;
  }
}
