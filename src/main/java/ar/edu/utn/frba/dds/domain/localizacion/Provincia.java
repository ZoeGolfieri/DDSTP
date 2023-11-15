package ar.edu.utn.frba.dds.domain.localizacion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Provincia {
  String nombre;

  public Provincia(String nombre) {
    this.nombre = nombre;
  }


}
