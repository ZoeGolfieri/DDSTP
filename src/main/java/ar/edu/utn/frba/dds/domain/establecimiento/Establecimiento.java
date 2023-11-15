package ar.edu.utn.frba.dds.domain.establecimiento;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.localizacion.Localizacion;
import ar.edu.utn.frba.dds.domain.localizacion.division.Division;
import ar.edu.utn.frba.dds.domain.localizacion.division.TipoDivision;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioComunidad;
import ar.edu.utn.frba.dds.domain.servicio.EstadoIncidente;
import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import java.util.ArrayList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

/*estaciones y sucursales*/
@Entity
@Table(name = "Establecimientos")
@Getter
@Setter
@NoArgsConstructor
public class Establecimiento  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_establecimiento")
  private Integer id_establecimiento;

  // Atributos
  @Column(name = "nombre", columnDefinition = "VARCHAR(20)")
  private String nombre;
  @Column(name = "tipoEstablecimiento")
  @Enumerated
  private TipoEstablecimiento tipoEstablecimiento;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "provincia", column = @Column(name = "establecimiento_provincia")),
      @AttributeOverride(name = "division.nombre", column = @Column(name = "establecimiento_division_nombre")),
      @AttributeOverride(name = "division.tipo", column = @Column(name = "establecimiento_division_tipo")),
      @AttributeOverride(name = "latitud", column = @Column(name = "establecimiento_latitud")),
      @AttributeOverride(name = "longitud", column = @Column(name = "establecimiento_longitud")),
  })
  private Localizacion localizacion;
  @OneToMany
  private List<Servicio> servicios;

  public Establecimiento(String nombre, TipoEstablecimiento tipoEstablecimiento, Localizacion localizacion) {
    this.nombre = nombre;
    this.tipoEstablecimiento = tipoEstablecimiento;
    this.servicios = new ArrayList<>();
    this.localizacion = localizacion;

  }

  // Metodos

  /*
  public void avisarIncidentes(){
  if(!!getservicios().map(servicio->servicio.getIncidentes).isEmpty())  
    avisaALosCercanos(); repoUsuarios.getUsuarios.filter(u.ubicacionAcutla = servicioLocalizacion);
  }
  
  */  
  // Tendriamos que hacer los test
  public void darAltaServicio(Servicio servicioNuevo) {
    this.servicios.add(servicioNuevo);
  }

  public void darBajaServicio(Servicio servicioObsoleto) {
    this.servicios.remove(servicioObsoleto);
  }

  public List<Servicio>  estaCerca(Usuario usuario) {

    if(this.localizacion.estaCerca(usuario.getLocalizacion())){

      List<Comunidad> comunidades = RepositorioComunidad.getInstancia()
          .comunidadesALasQuePertenece(usuario);
      List<Servicio> serviciosConIncidentes = this.servicios.stream().filter(servicio -> {
        return !(servicio.getIncidentes().stream().filter(incidente -> {
          return  comunidades.contains(incidente.getComunidad()) && incidente.getEstado().equals(EstadoIncidente.ACTIVO);
        }).collect(Collectors.toList()).isEmpty());
      }).collect(Collectors.toList());

      if(!serviciosConIncidentes.isEmpty()){
        usuario.notificarServiciosCercanos(serviciosConIncidentes);
      }
      return serviciosConIncidentes;
    }
  return null;
  }

}
