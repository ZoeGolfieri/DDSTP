package ar.edu.utn.frba.dds.domain.Ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import ar.edu.utn.frba.dds.domain.entidad.Entidad;
import ar.edu.utn.frba.dds.exceptions.RutaInvalidaException;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;

import com.opencsv.CSVWriter;


public class RankingPorCantidad implements Criterio{

  @Override
  public void calcularRanking(List<Entidad> entidades) {
    Collections.sort(entidades, Comparator.comparingInt(Entidad::cantidadIncidentesEnUnaSemana).reversed());
    generarCSVConNumeracion(entidades.stream().map(Entidad :: getNombreEntidad).toList(), "RankingsCSV/rankingPorCantidad.csv");
  }
}







