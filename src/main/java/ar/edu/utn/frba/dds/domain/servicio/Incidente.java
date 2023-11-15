package ar.edu.utn.frba.dds.domain.servicio;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;
@Entity
@Table(name = "Incidentes")
@NoArgsConstructor
public class Incidente{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_incidente")
  @Getter
  private Integer id_incidente;
  @Getter
  @Column(name = "observaciones", columnDefinition = "VARCHAR(250)")
  private String observaciones;
  @Getter
  @Enumerated
  @Setter
  private EstadoIncidente estado;
  @Getter
  @ManyToOne
  @JoinColumn(name = "comunidad_id", referencedColumnName = "id_comunidad")
  private Comunidad comunidad;
  @Getter
  @Column(name = "horarioApertura", columnDefinition = "DATE")
  private LocalDateTime horarioApertura;
  @Setter
  @Getter
  @Column(name = "horarioCierre", columnDefinition = "DATE")
  private LocalDateTime horarioCierre;

  public Incidente(String observaciones, Comunidad comunidad){
    this.observaciones = observaciones;
    this.estado = EstadoIncidente.ACTIVO;
    this.horarioApertura = LocalDateTime.now();
    this.comunidad = comunidad;
  }
  public void cerrarIncidente(){
    this.setHorarioCierre(LocalDateTime.now());
    this.estado = EstadoIncidente.RESUELTO;
  }

  public long tiempoCierre(){
    Duration duracion = Duration.between(this.horarioApertura, this.horarioCierre);
    long cantidadHoras = duracion.toHours();
    return cantidadHoras;
  }

  public boolean esDeLaComunidad(Comunidad comunidad){
    return this.comunidad.equals(comunidad);
  }

  public boolean suEstadoEs(EstadoIncidente estado){
    return this.estado.equals(estado);
  }

  // como el incidente tiene a la comunidad, informa a sus usuarios cercanos.
}
