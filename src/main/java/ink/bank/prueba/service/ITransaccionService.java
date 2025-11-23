package ink.bank.prueba.service;

import ink.bank.prueba.dto.ResultRes;
import ink.bank.prueba.dto.anulartransaccion.AnulacionReq;
import ink.bank.prueba.dto.obtenertransaccion.TransactionRes;
import ink.bank.prueba.dto.registrartransaccion.TransaccionReq;

/** Servicio para manejar transacciones. */
public interface ITransaccionService {

  /**
   * Obtiene una transacción por su ID.
   *
   * @param transactionId ID de la transacción a obtener.
   * @return La transacción correspondiente al ID proporcionado.
   */
  TransactionRes obtenerTransaccion(Long transactionId);

  /**
   * Realiza una transacción de compra.
   *
   * @param transaccionReq Datos de la transacción a realizar.
   * @return Resultado de la transacción.
   */
  ResultRes realizarTransaccion(TransaccionReq transaccionReq);

  /**
   * Anula una transacción existente.
   *
   * @param anulacionReq Datos de la transacción a anular.
   * @return Resultado de la anulación.
   */
  ResultRes anularTransaccion(AnulacionReq anulacionReq);
}
