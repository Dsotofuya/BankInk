package ink.bank.prueba.jpa.entity;

import ink.bank.prueba.enums.EstadoTransaccionEnum;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entidad para la tabla TRANSACCIONES. */
@Entity
@Table(name = "TRANSACCIONES")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TransaccionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_TRANSACCION")
  private Long id;

  @Column(name = "MONTO_TRANSACCION")
  private BigDecimal montoTransaccion;

  @Column(name = "FECHA_TRANSACCION")
  private Date fechaTransaccion;

  @Column(name = "DIVISA_TRANSACCION")
  private String divisaTransaccion;

  @Enumerated(EnumType.STRING)
  @Column(name = "ESTADO_TRANSACCION")
  private EstadoTransaccionEnum estadoTransaccion;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "NRO_TARJETA_TRANSACCION", referencedColumnName = "NRO_TARJETA")
  private TarjetaEntity tarjeta;
}
