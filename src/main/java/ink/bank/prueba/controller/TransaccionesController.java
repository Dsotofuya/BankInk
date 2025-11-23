package ink.bank.prueba.controller;

import ink.bank.prueba.dto.ResultRes;
import ink.bank.prueba.dto.anulartransaccion.AnulacionReq;
import ink.bank.prueba.dto.obtenertransaccion.TransactionRes;
import ink.bank.prueba.dto.registrartransaccion.TransaccionReq;
import ink.bank.prueba.service.ITransaccionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controlador para gestionar las operaciones relacionadas con las transacciones. */
@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
public class TransaccionesController {

  private final ITransaccionService transaccionService;

  /**
   * Obtiene los detalles de una transacción por su ID.
   *
   * @param transactionId El ID de la transacción a obtener.
   * @return Respuesta HTTP con los detalles de la transacción.
   */
  @Operation(
      summary = "Obtiene los detalles de una transacción por su ID.",
      description = "Respuesta HTTP con los detalles de la transacción.")
  @GetMapping("/{transactionId}")
  public ResponseEntity<TransactionRes> obtenerTransaccion(@PathVariable Long transactionId) {
    return new ResponseEntity<>(
        transaccionService.obtenerTransaccion(transactionId), HttpStatus.OK);
  }

  /**
   * Realiza una transacción de compra.
   *
   * @param transaccionReq Objeto con los detalles de la transacción a realizar.
   * @return Respuesta HTTP con el resultado de la transacción.
   */
  @Operation(
      summary = "Realiza una transacción de compra.",
      description = " Respuesta HTTP con el resultado de la transacción.")
  @PostMapping("/purchase")
  public ResponseEntity<ResultRes> realizarTransaccion(
      @RequestBody @Valid TransaccionReq transaccionReq) {
    return new ResponseEntity<>(
        transaccionService.realizarTransaccion(transaccionReq), HttpStatus.OK);
  }

  /**
   * Anula una transacción existente.
   *
   * @param anulacionReq Objeto con los detalles de la transacción a anular.
   * @return Respuesta HTTP con el resultado de la anulación.
   */
  @Operation(
      summary = "Anula una transacción existente.",
      description = " Respuesta HTTP con el resultado de la anulación.")
  @PostMapping("/anulation")
  public ResponseEntity<ResultRes> anularTransaccion(
      @RequestBody @Valid AnulacionReq anulacionReq) {
    return new ResponseEntity<>(transaccionService.anularTransaccion(anulacionReq), HttpStatus.OK);
  }
}
