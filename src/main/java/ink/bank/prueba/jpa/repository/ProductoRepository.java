package ink.bank.prueba.jpa.repository;

import ink.bank.prueba.jpa.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repositorio para manejar productos. */
@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {}
