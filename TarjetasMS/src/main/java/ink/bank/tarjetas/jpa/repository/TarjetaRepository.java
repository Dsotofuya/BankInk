package ink.bank.tarjetas.jpa.repository;

import ink.bank.tarjetas.jpa.entity.TarjetaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repositorio para manejar tarjetas. */
@Repository
public interface TarjetaRepository extends JpaRepository<TarjetaEntity, Long> {

  /**
   * Buscar una tarjeta por su número.
   *
   * @param numeroTarjeta Número de la tarjeta.
   * @return TarjetaEntity envuelta en un Optional.
   */
  Optional<TarjetaEntity> findByNumeroTarjeta(String numeroTarjeta);
}
