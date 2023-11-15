package ar.edu.utn.frba.dds.domain.usuario;

import static ar.edu.utn.frba.dds.domain.servicio.EstadoIncidente.ACTIVO;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.medioComunicacion.MedioComunicacion;
import ar.edu.utn.frba.dds.domain.establecimiento.Establecimiento;
import ar.edu.utn.frba.dds.domain.localizacion.Localizacion;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioComunidad;
import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import ar.edu.utn.frba.dds.domain.validadores.ValidadorContrasenias;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "usuarios")
@NoArgsConstructor
public class Usuario{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario")
  private Integer id_usuario;
  // Atributos
  @Column(name = "apellido", columnDefinition = "VARCHAR(20)")
  private String apellido;
  @Column(name = "nombre", columnDefinition = "VARCHAR(20)", insertable = false, updatable = false)
  private String nombre;
  @Column(name = "email", columnDefinition = "VARCHAR(40)")
  private String email;
  @Column(name = "telefono", columnDefinition = "VARCHAR(10)")
  private String telefono;

  @ElementCollection
  private List<RangoHorario> horariosNotificacion = new ArrayList<>();
  @ManyToOne
  @JoinColumn(name = "medioComunicacion_id", referencedColumnName = "id_medioComunicacion")
  private MedioComunicacion medioComunicacion;
  @Column(name = "ultimaHoraNotificacion", columnDefinition = "DATE")
  private LocalDateTime ultimaHoraNotificacion;

  @Column(name = "nombreUsuario", columnDefinition = "VARCHAR(255)")
  private String nombreUsuario;
  @Column(name = "contrasenia", columnDefinition = "VARCHAR(255)")
  private String hashContrasenia;
  @Column(name = "localizacion")
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "provincia", column = @Column(name = "usuario_provincia")),
      @AttributeOverride(name = "division.nombre", column = @Column(name = "usuario_division_nombre")),
      @AttributeOverride(name = "division.tipo", column = @Column(name = "usuario_division_tipo")),
      @AttributeOverride(name = "latitud", column = @Column(name = "usuario_latitud")),
      @AttributeOverride(name = "longitud", column = @Column(name = "usuario_longitud")),
  })
  private Localizacion localizacion;
  @Column(name = "localizacion_actual")
  @Embedded
  private Localizacion localizacion_actual;
  @ManyToMany
  @JoinTable(
      name = "usuario_establecimiento", // Nombre de la tabla intermedia
      joinColumns = @JoinColumn(name = "id_usuario"), // Columna que hace referencia a esta entidad
      inverseJoinColumns = @JoinColumn(name = "id_establecimiento") // Columna que hace referencia a la otra entidad
  )
  private List<Establecimiento> establecimientosInteres = new ArrayList<>();
  @ManyToMany
  @JoinTable(
      name = "usuario_servicio", // Nombre de la tabla intermedia
      joinColumns = @JoinColumn(name = "id_usuario"), // Columna que hace referencia a esta entidad
      inverseJoinColumns = @JoinColumn(name = "id_servicio") // Columna que hace referencia a la otra entidad
  )
  private List<Servicio> serviciosInteres = new ArrayList<>();
  @Transient
  private ValidadorContrasenias validador = new ValidadorContrasenias();

  // Metodos
  public Usuario(String nombreUsuario, String contrasenia) {
    validador.validarContrasenia(nombreUsuario,contrasenia);
    this.nombreUsuario = nombreUsuario;
    this.setContrasenia(contrasenia);
  }

  public void setContrasenia(String contrasenia) {
    this.hashContrasenia = DigestUtils.sha256Hex(contrasenia);
  }

  // ============== Notificar incidentes

  private boolean estaEnRangoHorario() {
    LocalTime horaActual = LocalTime.now();
    int horaEntero = horaActual.getHour();
    return this.horariosNotificacion.stream().anyMatch(unRango -> unRango.laHoraPertenece(horaEntero));
  }

  public void notificarIncidente(Incidente incidente) {
    if(this.estaEnRangoHorario()) {
      medioComunicacion.notificarIncidente(this, incidente);
      this.ultimaHoraNotificacion = LocalDateTime.now();
    }
  }

  public void notificarIncidentes() { //Pendientes de notificación
    if(this.estaEnRangoHorario()) {
      List<Comunidad> comunidadesUsuario = RepositorioComunidad.getInstancia().getComunidades().stream().filter(unaComunidad -> unaComunidad.usuarioEsParte(this)).collect(Collectors.toList());

      List<Incidente> incidentesAbiertos = comunidadesUsuario.stream()
                                                      .map(unaComunidad -> unaComunidad.consultarIncidentesPorEstado(ACTIVO))
                                                      .flatMap(Collection::stream)
                                                      .collect(Collectors.toList());
      List<Incidente> incidentesANotificar = incidentesAbiertos.stream()
                                              .filter(unIncidente -> unIncidente.getHorarioApertura().isAfter(this.ultimaHoraNotificacion))
                                              .collect(Collectors.toList());

      incidentesANotificar.stream().forEach(unIncidente -> medioComunicacion.notificarIncidente(this, unIncidente) );
    }
  }

  // ================ Otros

  public void agregarHorarioNotificacion(RangoHorario nuevoRango){
    horariosNotificacion.add(nuevoRango);
  }

  public void notificarServiciosCercanos(List<Servicio> servicios){
    medioComunicacion.notificarServicioCercano(this, servicios);
  }
}


//métodos anteriores POR LAS DUDAS LOS DEJÉ
/*
 private boolean estaEnRangoHorario() {
    LocalTime horaActual = LocalTime.now();
    int horaEntero = horaActual.getHour();
    return !(this.horariosNotificacion.stream().filter(rango -> {
      return rango.getInicio() < horaEntero && rango.getFin() > horaEntero;
    }).collect(Collectors.toList()).isEmpty());
  }

  public void notificarIncidente(Incidente incidente) {
    if(this.estaEnRangoHorario()) {
      medioComunicacion.notificarIncidente(this, incidente);
    }
  }

  // Sugerencia: medioComunicacion tiene que tener un metodo solo que sea notificar, y todo lo q
  // se manda se transforma en string

  public void notificarIncidentes() {
    if(this.estaEnRangoHorario()) {
      Comunidad comunidad = (Comunidad) RepositorioComunidad.getInstancia().getComunidades().stream().filter(c -> {
        return c.usuarioEsParte(this);
      });
      List<Incidente> incidentes = comunidad.obtenerIncidentesDeInteres(this);
      medioComunicacion.notificarIncidentes(this, incidentes);
    }
  }

*/