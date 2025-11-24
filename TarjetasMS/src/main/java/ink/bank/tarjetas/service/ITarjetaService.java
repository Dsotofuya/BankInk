package ink.bank.tarjetas.service;

import ink.bank.tarjetas.dto.ResultRes;
import ink.bank.tarjetas.dto.activartarjeta.ActivarTarjetaReq;
import ink.bank.tarjetas.dto.consultarbalance.ConsultarBalanceRes;
import ink.bank.tarjetas.dto.generarnumerotarjeta.GeneracionTarjetaRes;
import ink.bank.tarjetas.dto.recargarbalance.RecargarBalanceReq;
import ink.bank.tarjetas.exception.ElementoNoEncontradoException;
import ink.bank.tarjetas.jpa.entity.TarjetaEntity;

/** Interfaz del servicio para la gestión de tarjetas. */
public interface ITarjetaService {

  /**
   * Genera un número de tarjeta para un producto dado.
   *
   * @param productId El ID del producto para el cual se generará el número de tarjeta.
   * @return Objeto GeneracionTarjetaRes que contiene el número de tarjeta generado.
   */
  GeneracionTarjetaRes generarNumeroTarjeta(Long productId);

  /**
   * Activa una tarjeta dada su número.
   *
   * @param numeroTarjeta El número de la tarjeta a activar.
   * @return Objeto ResultRes que contiene el resultado de la activación.
   */
  ResultRes activarTarjeta(ActivarTarjetaReq numeroTarjeta);

  /**
   * Bloquea una tarjeta por su número.
   *
   * @param cardId El numero de la tarjeta a bloquear.
   * @return Objeto ResultRes que contiene el resultado del bloqueo.
   */
  ResultRes bloquearTarjeta(String cardId);

  /**
   * Recarga el balance de una tarjeta.
   *
   * @param recargarBalanceReq Objeto que contiene la información para recargar el balance.
   * @return Objeto ResultRes que contiene el resultado de la recarga.
   */
  ResultRes recargarBalance(RecargarBalanceReq recargarBalanceReq);

  /**
   * Consulta el balance de una tarjeta por su número.
   *
   * @param cardId El número de la tarjeta cuyo balance se desea consultar.
   * @return Objeto ResultRes que contiene el balance de la tarjeta.
   */
  ConsultarBalanceRes consultarBalance(String cardId);

  /**
   * Consulta una tarjeta por su número.
   *
   * @param numeroTarjeta Número de la tarjeta.
   * @return TarjetaEntity encontrada.
   * @throws ElementoNoEncontradoException Si la tarjeta no es encontrada.
   */
  TarjetaEntity consultarTarjeta(String numeroTarjeta);
}
