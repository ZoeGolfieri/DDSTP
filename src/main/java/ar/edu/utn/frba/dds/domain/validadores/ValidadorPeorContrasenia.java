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
  private String nombreArchivo = "contraseniasPeligrosas.txt";
@Getter
  private static ValidadorPeorContrasenia INSTANCE = new ValidadorPeorContrasenia();

  public static ValidadorPeorContrasenia getInstance() {
    return INSTANCE;
  }

  public void validarPosiblePeorContrasenia(String contrasenia) {
    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(new FileInputStream(obtenerRutaArchivo()), StandardCharsets.UTF_8))) {

      String linea;
      while ((linea = br.readLine()) != null) {
        if (contrasenia.equals(linea.trim())) {
          throw new MalaContraseniaException("La contraseña se encuentra entre las peores 10000");
        }
      }
    } catch (IOException e) {
      // Loguea detalles de la excepción
      e.printStackTrace();
      throw new RutaInvalidaException("No se encontró el archivo en la ruta indicada");
    }
  }

  private String obtenerRutaArchivo() {
    Path path = Paths.get("src", "main", "resources", nombreArchivo);
    return path.toAbsolutePath().toString();
  }
}