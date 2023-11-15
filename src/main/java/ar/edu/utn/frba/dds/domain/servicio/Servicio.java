package ar.edu.utn.frba.dds.domain.servicio;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioComunidad;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "Servicios")
@NoArgsConstructor
public class Servicio {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_servicio")
  private Integer id_servicio;

  // Atributos
  @Column(name = "tipoServicio")
  @Enumerated
  private TipoServicio tipo;
  @Column(name = "fueraDeFuncionamiento", columnDefinition = "BOOLEAN")
  private boolean fueraDeFuncionamiento;
  @OneToMany
  private List<Incidente> incidentes;

  // Metodos

  public Servicio(TipoServicio tipo){
    this.tipo = tipo;
    this.incidentes = new ArrayList<>();
  }

  public void aniadirIncidente(Incidente incidente) {
      this.incidentes.add(incidente);
  }

  public void informarNoFuncionamiento(String observaciones) {
   this.comunidadesInteresadasEnElServicio().forEach(comunidad->
   {
    Incidente incidente = new Incidente(observaciones, comunidad);
    this.incidentes.add(incidente);
    //comunidad.reportarIncidente(incidente);
    });
  }

  private List<Comunidad> comunidadesInteresadasEnElServicio(){ // DEBERÍA ESTAR ACA O EN EL REPO DE COMUNIDADES ?
    return RepositorioComunidad.getInstancia().getComunidades().stream().
        filter(comunidad -> comunidad.estaInteresaEnServicio(this)).toList();
  }

  public List<Incidente> incidentesDeComunidad(Comunidad comunidad){
    return incidentes.stream()
        .filter(incidente -> incidente.esDeLaComunidad(comunidad))
        .collect(Collectors.toList());
  }

}




//métodos anteriores POR LAS DUDAS LOS DEJÉ
/*
public List<Incidente> obtenerIncidentesAbiertosDeComunidad(Comunidad comunidad){
    return incidentes.stream()
        .filter(incidente -> {
          return incidente.getComunidad().equals(comunidad)
          && incidente.getEstado().equals(EstadoIncidente.ACTIVO);
        })
        .collect(Collectors.toList());
  }

*/