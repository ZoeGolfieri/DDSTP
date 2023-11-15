package ar.edu.utn.frba.dds.domain.servicioLocalizacion;

import ar.edu.utn.frba.dds.domain.localizacion.Localizacion;
import ar.edu.utn.frba.dds.domain.localizacion.Provincia;
import ar.edu.utn.frba.dds.domain.localizacion.division.Division;

public interface ServicioLocalizacion {
  public Localizacion buscarMunicipio(String nombreProvincia, String nombreMunicipio);
  public Localizacion buscarDepartamento(String nombreProvincia, String nombreDepartamento);
  public Localizacion buscarProvincia(String nombreProvincia);

  public Localizacion obtenerUbicacionActual();//lo planteo en la misma interfaz para que no haga ruido
  //esto a su vez podriamos hacer que se ejecute cada cierto tiempo para los usuarios
  // y les cambiaria la ubi actual,como lo vamos a hacer
  //es una buena pregunta, pero para ir planteandolo
  public Localizacion obtenerUbicacion(String longitud, String latitud); // en caso de q transforme la long y lat en una ubc

}
