package ink.bank.prueba.dto.consultarbalance;

import ink.bank.prueba.dto.ResultRes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/** DTO para la respuesta de consultar balance. */
@Getter
@Setter
@Schema(description = "DTO para la respuesta de consultar balance")
public class ConsultarBalanceRes extends ResultRes {

  @Schema(description = "Balance de la tarjeta", example = "99.00")
  private String balance;

  @Builder
  public ConsultarBalanceRes(String status, String message, String balance) {
    super(status, message);
    this.balance = balance;
  }
}
