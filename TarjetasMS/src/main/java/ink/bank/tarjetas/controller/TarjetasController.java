package ink.bank.tarjetas.controller;

import ink.bank.tarjetas.dto.ResultRes;
import ink.bank.tarjetas.dto.activartarjeta.ActivarTarjetaReq;
import ink.bank.tarjetas.dto.consultarbalance.ConsultarBalanceRes;
import ink.bank.tarjetas.dto.generarnumerotarjeta.GeneracionTarjetaRes;
import ink.bank.tarjetas.dto.recargarbalance.RecargarBalanceReq;
import ink.bank.tarjetas.service.ITarjetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controlador para gestionar las operaciones relacionadas con las tarjetas. */
@RestController
@RequestMapping("card")
@RequiredArgsConstructor
@Tag(name = "card", description = "Operaciones relacionadas con las tarjetas")
public class TarjetasController {

  private final ITarjetaService tarjetaService;

  /**
   * Genera un número de tarjeta para un producto dado.
   *
   * @param productId El ID del producto para el cual se generará el número de tarjeta.
   * @return Respuesta HTTP con el número de tarjeta generado.
   */
  @Operation(
      summary = "Genera un número de tarjeta para un producto dado",
      description = "DRespuesta HTTP con el número de tarjeta generado.")
  @GetMapping("/{productId}/number")
  public ResponseEntity<GeneracionTarjetaRes> generarNumeroTarjeta(@PathVariable Long productId) {
    return new ResponseEntity<>(tarjetaService.generarNumeroTarjeta(productId), HttpStatus.CREATED);
  }

  /**
   * Activa una tarjeta dada su número.
   *
   * @param activarTarjetaReq Objeto con el número de la tarjeta a activar.
   * @return Respuesta HTTP con el resultado de la activación.
   */
  @PostMapping("/enroll")
  @Operation(
      summary = "Activa una tarjeta dada su número",
      description = "Respuesta HTTP con el resultado de la activación.")
  public ResponseEntity<ResultRes> activarTarjeta(
      @RequestBody @Valid ActivarTarjetaReq activarTarjetaReq) {
    return new ResponseEntity<>(
        tarjetaService.activarTarjeta(activarTarjetaReq), HttpStatus.ACCEPTED);
  }

  /**
   * Bloquea una tarjeta por su número.
   *
   * @param cardId El número de la tarjeta a bloquear.
   * @return Respuesta HTTP con el resultado del bloqueo.
   */
  @Operation(
      summary = "Bloquea una tarjeta por su número",
      description = "espuesta HTTP con el resultado del bloqueo.")
  @DeleteMapping("/{cardId}")
  public ResponseEntity<ResultRes> bloquearTarjeta(@PathVariable String cardId) {
    return new ResponseEntity<>(tarjetaService.bloquearTarjeta(cardId), HttpStatus.ACCEPTED);
  }

  /**
   * Recarga el balance de una tarjeta.
   *
   * @param recargarBalanceReq Objeto con la información para recargar el balance.
   * @return Respuesta HTTP con el resultado de la recarga.
   */
  @Operation(
      summary = "Recarga el balance de una tarjeta",
      description = "Respuesta HTTP con el resultado de la recarga.")
  @PostMapping("/balance")
  public ResponseEntity<ResultRes> recargarBalance(
      @RequestBody @Valid RecargarBalanceReq recargarBalanceReq) {
    return new ResponseEntity<>(
        tarjetaService.recargarBalance(recargarBalanceReq), HttpStatus.ACCEPTED);
  }

  /**
   * Consulta el balance de una tarjeta por su número.
   *
   * @param cardId El número de la tarjeta cuyo balance se desea consultar.
   * @return Respuesta HTTP con el balance de la tarjeta.
   */
  @Operation(
      summary = "Consulta el balance de una tarjeta por su número",
      description = "Respuesta HTTP con el balance de la tarjeta.")
  @GetMapping("/balance/{cardId}")
  public ResponseEntity<ConsultarBalanceRes> consultarBalance(@PathVariable String cardId) {
    return new ResponseEntity<>(tarjetaService.consultarBalance(cardId), HttpStatus.ACCEPTED);
  }
}
