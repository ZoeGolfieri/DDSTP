package ar.edu.utn.frba.dds.domain.Ranking;

import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.opencsv.CSVWriter;

import ar.edu.utn.frba.dds.domain.entidad.Entidad;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;

import com.opencsv.CSVWriter;

public class RankingPorPromedioCierre implements Criterio{//esto es mas facil si en ves de herencia

  @Override
  public void calcularRanking(List<Entidad> entidades) {
      Collections.sort(entidades, Comparator.comparingDouble(Entidad::promedioDeCierreIncidente).reversed());
      generarCSVConNumeracion(entidades.stream().map(Entidad :: getNombreEntidad).toList(), "RankingsCSV/rankingPorPromedio.csv");
    }  
}
