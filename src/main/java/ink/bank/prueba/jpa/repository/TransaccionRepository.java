package ink.bank.prueba.jpa.repository;

import ink.bank.prueba.jpa.entity.TransaccionEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repositorio para manejar transacciones. */
@Repository
public interface TransaccionRepository extends JpaRepository<TransaccionEntity, Long> {

  /**
   * Busca una transacción por su ID y el número de tarjeta asociado.
   *
   * @param id Id de la transacción.
   * @param numeroTarjeta Número de la tarjeta asociada a la transacción.
   * @return Optional con la transacción si se encuentra.
   */
  Optional<TransaccionEntity> findByIdAndTarjetaNumeroTarjeta(Long id, String numeroTarjeta);
}
