package ar.edu.utn.frba.dds.domain.localizacion;

import ar.edu.utn.frba.dds.domain.localizacion.division.Division;
import ar.edu.utn.frba.dds.domain.servicioLocalizacion.ServicioLocalizacionGeoRefApi;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Localizacion {

  // Atributos

  String provincia;
  @Embedded
  Division division;
  Double latitud;
  Double longitud;

  // Metodos

  public Localizacion(String provincia, Division division) {
    this.division = division;
    this.provincia = provincia;
  }

  public Localizacion(String provincia, Division division, Double latitud, Double longitud) {
    this.provincia = provincia;
    this.division = division;
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public boolean estaCerca(Localizacion localizacion) {
    double distanciaLatitud = this.getLatitud()- localizacion.getLatitud();
    double distanciaLongitud = this.getLongitud() - localizacion.getLongitud();
    return  100<Math.sqrt(distanciaLatitud * distanciaLatitud + distanciaLongitud * distanciaLongitud);
  }

}