package ar.edu.utn.frba.dds.domain.localizacion.division;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Division {

  String nombre;
  @Enumerated
  TipoDivision tipo;

  public Division(String nombre, TipoDivision tipo) {
    this.nombre = nombre;
    this.tipo = tipo;
  }
}
