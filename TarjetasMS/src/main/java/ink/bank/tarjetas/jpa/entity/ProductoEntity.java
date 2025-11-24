package ink.bank.tarjetas.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entidad para la tabla PRODUCTOS. */
@Entity
@Table(name = "PRODUCTOS")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_PRODUCTO")
  private Long id;

  @Column(name = "PRIMER_NOMBRE")
  private String primerNombre;

  @Column(name = "SEGUNDO_NOMBRE")
  private String segundoNombre;

  @Column(name = "PRIMER_APELLIDO")
  private String primerApellido;

  @Column(name = "SEGUNDO_APELLIDO")
  private String segundoApellido;
}
