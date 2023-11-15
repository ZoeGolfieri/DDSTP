package ar.edu.utn.frba.dds.domain.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class RangoHorario {

  // Atributos
  private int horaInicio;
  private int horaFin;

  // Metodos


  public RangoHorario(int inicio, int fin) {
    this.horaInicio = inicio;
    this.horaFin = fin;
  }

  public boolean laHoraPertenece(int hora){
    return hora >= this.horaInicio && hora < this.horaFin;
  }



}
