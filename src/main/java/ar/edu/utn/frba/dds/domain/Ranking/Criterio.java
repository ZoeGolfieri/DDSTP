package ar.edu.utn.frba.dds.domain.Ranking;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import ar.edu.utn.frba.dds.domain.entidad.Entidad;

public interface Criterio {

  public void calcularRanking(List<Entidad> entidades);
  
  //NO SABEMOS SI USAR UNA INTERFAZ CON COMPORTAMIENTO ESTA BIEN
  default void generarCSVConNumeracion(List<String> listaStrings, String nombreArchivo) {
      try {
          FileWriter csvWriter = new FileWriter(nombreArchivo);

          for (int i = 0; i < listaStrings.size(); i++) {
              String elemento = listaStrings.get(i);
              String linea = (i + 1) + ", " + elemento;

              csvWriter.append(linea);
              csvWriter.append("\n");
          }

          csvWriter.flush();
          csvWriter.close();
      } catch (IOException e) {
          System.out.println("Error al generar el archivo CSV: " + e.getMessage());
      }    
  }
  
}
