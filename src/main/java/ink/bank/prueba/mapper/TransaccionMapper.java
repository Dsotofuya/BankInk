package ink.bank.prueba.mapper;

import ink.bank.prueba.dto.TransaccionDTO;
import ink.bank.prueba.jpa.entity.TransaccionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Mapper para convertir entre entidades de transacción y DTOs. */
@Mapper(componentModel = "spring")
public interface TransaccionMapper {

  /**
   * Convierte una entidad de transacción a un DTO.
   *
   * @param entity Entidad de transacción.
   * @return DTO de transacción.
   */
  @Mapping(target = "numeroTarjeta", source = "tarjeta.numeroTarjeta")
  TransaccionDTO toDto(TransaccionEntity entity);
}
