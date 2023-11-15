package ar.edu.utn.frba.dds.domain.validadores;

import ar.edu.utn.frba.dds.exceptions.MalaContraseniaException;
import ar.edu.utn.frba.dds.exceptions.RutaInvalidaException;
import lombok.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class ValidadorPeorContrasenia {
  @Getter
  @Setter
  private String nombreArchivo = "contraseniasPeligrosas.txt"; // Esto tendría que estar aca?
  @Getter
  private static ValidadorPeorContrasenia INSTANCE = new ValidadorPeorContrasenia();

  public void validarPosiblePeorContrasenia(String contrasenia) {
    try{


      Path path = Paths.get("src", "main", "resources", nombreArchivo);
      String absolutePath = path.toAbsolutePath().toString();

      BufferedReader br = new BufferedReader(
          new InputStreamReader(new FileInputStream(absolutePath),
              StandardCharsets.UTF_8));

      String linea;
      while ((linea = br.readLine()) != null) {
        if (contrasenia.contains(linea)) { // cambie el equals por contains
          throw new MalaContraseniaException("La contrasenia se encuentra entre las peores 10000");
        }
      }
      br.close();
    }catch (IOException e){
      throw new RutaInvalidaException("No se encontro el archivo en la ruta indicada");
    }

  }

}
