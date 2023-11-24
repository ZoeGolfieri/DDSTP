package ar.edu.utn.frba.dds.domain.validadores;

import ar.edu.utn.frba.dds.domain.Main;
import ar.edu.utn.frba.dds.exceptions.MalaContraseniaException;
import ar.edu.utn.frba.dds.exceptions.RutaInvalidaException;
import lombok.*;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class ValidadorPeorContrasenia {
  @Getter
  String nombreArchivoActual;

  private static final ValidadorPeorContrasenia INSTANCE = new ValidadorPeorContrasenia();

  public static ValidadorPeorContrasenia getInstance() {
    return INSTANCE;
  }

  public void validarPosiblePeorContrasenia(String contrasenia) {

    String fileText = getSourceString("com/proyecto/recursos/file.txt");

    if(fileText == null) return;

    Stream<String> lines = fileText.lines();
    lines.forEach(s -> {
      if (contrasenia.contains(s)) { // cambie el equals por contains
        throw new MalaContraseniaException("La contrasenia se encuentra entre las peores 10000");
      }
    });

  }

  public String obtenerRutaArchivo(String nombreArchivo) {
    Path path = Paths.get("src", "main", "resources", nombreArchivo);
    nombreArchivoActual = path.toAbsolutePath().toString();
    return nombreArchivoActual;
  }

  private static String getSourceString(String source) {
    InputStream inputStream = null;
    String stringFromBytes = null;

    try {

      inputStream = Main.class.getClassLoader().getResourceAsStream(source);

      if(inputStream == null)
        return null;

      byte[] bytesOfStream = inputStream.readAllBytes(); // leer los bytes del archivo de exportaciones.

      stringFromBytes = new String(bytesOfStream); // convertir los bytes a un string.

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      closeResources(inputStream);
    }

    return stringFromBytes;
  }

  private static void closeResources(Closeable stream) {
    if(stream != null) {
      try {
        stream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

