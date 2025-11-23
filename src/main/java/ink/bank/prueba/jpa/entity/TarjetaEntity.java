package ink.bank.prueba.jpa.entity;

import ink.bank.prueba.enums.EstadoTarjetaEnum;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entidad para la tabla TARJETAS. */
@Entity
@Table(name = "TARJETAS")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TarjetaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_TARJETA")
  private Long id;

  @Column(name = "NRO_TARJETA")
  private String numeroTarjeta;

  @Column(name = "NOMBRE_TITULAR")
  private String nombreTitular;

  @Column(name = "FECHA_VENCIMIENTO_TARJETA")
  private Date fechaVencimiento;

  @Enumerated(EnumType.STRING)
  @Column(name = "ESTADO_TARJETA")
  private EstadoTarjetaEnum estado;

  @Column(name = "DIVISA_TARJETA")
  private String divisa;

  @Column(name = "BALANCE_TARJETA")
  private BigDecimal balance;
}
