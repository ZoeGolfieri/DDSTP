package ar.edu.utn.frba.dds.domain.Comunidad;

import ar.edu.utn.frba.dds.domain.establecimiento.Establecimiento;
import ar.edu.utn.frba.dds.domain.servicio.EstadoIncidente;
import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "Comunidades")
@NoArgsConstructor
public class Comunidad {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_comunidad")
  private Integer id_comunidad;

  // Atributos
  @ManyToMany
  @JoinTable(
      name = "usuario_comunidad_miembro", // Nombre de la tabla intermedia
      joinColumns = @JoinColumn(name = "id_comunidad"), // Columna que hace referencia a esta entidad
      inverseJoinColumns = @JoinColumn(name = "id_usuario") // Columna que hace referencia a la otra entidad
  )
  private List<Usuario> miembros;

  @ManyToMany
  @JoinTable(
      name = "usuario_comunidad_administrador", // Nombre de la tabla intermedia
      joinColumns = @JoinColumn(name = "id_comunidad"), // Columna que hace referencia a esta entidad
      inverseJoinColumns = @JoinColumn(name = "id_usuario") // Columna que hace referencia a la otra entidad
  )
  private List<Usuario> administradores;
  @OneToMany
  private List<Servicio> serviciosDeInteres;
  @Getter
  @Column(name = "nombre", columnDefinition = "VARCHAR(100)")
  private String nombre;

  public Comunidad(List<Usuario> miembros, List<Usuario> administradores, List<Servicio> serviciosDeInteres, String nombre) {
    //VERIFICAR
    this.miembros = miembros;
    this.administradores = administradores;
    this.serviciosDeInteres = serviciosDeInteres;
    this.nombre = nombre;
  }
  public void reportarIncidente(Incidente incidente) {
    this.miembros.forEach(miembro -> miembro.notificarIncidente(incidente));
  }

  public boolean estaInteresaEnServicio(Servicio servicio) {
    return this.serviciosDeInteres.contains(servicio);
  }

  public void darDeBajaMiembro(Usuario miembro) {
    this.miembros.remove(miembro);
  }

  public void darDeAltaMiembro(Usuario miembroNuevo) {
    this.miembros.add(miembroNuevo);
  }

  public List<Incidente> consultarIncidentesPorEstado(EstadoIncidente estadoIncidente){
    return this.incidentesReportados().stream()
        .filter(unIncidente -> unIncidente.suEstadoEs(estadoIncidente))
        .collect(Collectors.toList());
  }

  public void solicitarServicio(Servicio servicio, Establecimiento establecimiento) {
    establecimiento.darAltaServicio(servicio); // tendria q existir un metodo en estacion que sea controlarSolicitudServicioNuevo o algo asi
  }

  public boolean usuarioEsParte(Usuario usuario){
    return miembros.contains(usuario);
  }

  public List<Incidente> incidentesReportados() {
    return this.serviciosDeInteres.stream()
        .map(servicio -> servicio.incidentesDeComunidad(this))
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

}


//métodos anteriores POR LAS DUDAS LOS DEJÉ
/*
public List<Incidente> obtenerIncidentesDeInteres(Usuario usuario) {
  return this.getServiciosDeInteres().stream()
      .filter(servicio -> usuario.getServiciosInteres().contains(servicio) )
      .map(servicio -> servicio.obtenerIncidentesAbiertosDeComunidad(this))
      .flatMap(Collection::stream)
      .collect(Collectors.toList());
}

  public List<Incidente> obtenerIncidentesReportados() {
    return this.getServiciosDeInteres().stream().map(servicio -> {
      return servicio.obtenerIncidentesAbiertosDeComunidad(this);
    }).flatMap(Collection::stream).collect(Collectors.toList());
  }

  public List<Usuario> usuariosInteresadosEn(Servicio servicio) {
    return this.miembros.stream().filter(usuario -> usuario.estaInteresado(servicio))
        .collect(Collectors.toList());
  }
*/