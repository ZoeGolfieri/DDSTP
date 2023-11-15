package ar.edu.utn.frba.dds.domain.repositorios;

import ar.edu.utn.frba.dds.domain.usuario.Empresa;
import ar.edu.utn.frba.dds.domain.usuario.TipoEmpresa;
import ar.edu.utn.frba.dds.exceptions.RutaInvalidaException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepositorioEmpresas {

  private List<Empresa> empresasUsuarias = new ArrayList<>();

  public List<Empresa> getEmpresasUsuarias() {
    return empresasUsuarias;
  }

  public String abrirArchivo(String nombreArchivo) {
    Path path = Paths.get("src", "main", "resources", nombreArchivo);
    String rutaCSV = path.toAbsolutePath().toString();
    return rutaCSV;
  }

  public void registrarEmpresas(String nombreArchivo) {
    try (CSVParser parser = new CSVParser(new FileReader(this.abrirArchivo(nombreArchivo)), CSVFormat.DEFAULT)) {
      for (CSVRecord record : parser) {
        String nombreEmpresa = record.get(0);
        String tipo = record.get(2);
        TipoEmpresa tipoEmpresa = TipoEmpresa.valueOf(tipo);
        this.registrarEmpresa(nombreEmpresa, tipoEmpresa);
      }
    } catch (IOException e) {
      throw new RutaInvalidaException("La ruta es invalida");
    }
  }

  public void registrarEmpresa(String nombreEmpresa, TipoEmpresa tipo) {
    empresasUsuarias.add(new Empresa(nombreEmpresa, tipo));
  }
}
