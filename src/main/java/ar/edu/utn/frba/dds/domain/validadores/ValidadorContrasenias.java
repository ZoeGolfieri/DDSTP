package ar.edu.utn.frba.dds.domain.validadores;

import ar.edu.utn.frba.dds.exceptions.CaracteresRepetidosException;
import ar.edu.utn.frba.dds.exceptions.ContraseniaMuyCortaException;
import ar.edu.utn.frba.dds.exceptions.UsaCrendencialesException;

public class ValidadorContrasenias {

  public ValidadorContrasenias(){}

  public ValidadorPeorContrasenia validador = new ValidadorPeorContrasenia();

  public void validarContrasenia(String nombreUsuario, String contrasenia) {

    this.validarTamanio(contrasenia);
    this.validacionCaracteresRepetidos(contrasenia);
    this.validarCredenciales(nombreUsuario, contrasenia);
    this.validarConPeoresContrasenias(contrasenia);
  }

  private void validacionCaracteresRepetidos(String contrasenia) {
    int len = contrasenia.length();
    for (int i = 0; i < len - 1; i++) {
      if (contrasenia.charAt(i) == contrasenia.charAt(i + 1)) {
        throw new CaracteresRepetidosException("La contrasenia tiene caracteres repetidos");
        // si se encuentra un par de caracteres simultáneos repetidos,
        // el string contiene caracteres simultáneos repetidos
      }
    }
  }

  public void validarConPeoresContrasenias(String contrasenia) {
    validador.validarPosiblePeorContrasenia(contrasenia);
  }

  public void cambiarArchivoPeoresContrasenias(String nuevoArchivo) {
    validador.setNombreArchivo(nuevoArchivo);
   }

  private void validarTamanio(String contrasenia) {
    if (contrasenia.length() <= 8) {
      throw new ContraseniaMuyCortaException("La contrasenia es demasiado corta");
    }
  }

  private void validarCredenciales(String nombreUsuario, String contrasenia) {
    if (nombreUsuario.equals(contrasenia)) {
      throw new UsaCrendencialesException("La contrasenia utiliza credenciales");
    }
  }



}
