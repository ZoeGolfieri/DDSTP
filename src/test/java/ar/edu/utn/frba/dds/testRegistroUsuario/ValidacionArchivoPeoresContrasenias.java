package ar.edu.utn.frba.dds.testRegistroUsuario;

import static org.junit.jupiter.api.Assertions.assertThrows;
import ar.edu.utn.frba.dds.domain.validadores.ValidadorPeorContrasenia;
import ar.edu.utn.frba.dds.exceptions.RutaInvalidaException;
import org.junit.jupiter.api.Test;

public class ValidacionArchivoPeoresContrasenias {

  @Test
  public void unaRutaIncorrectaGeneraExcepcion() {
    assertThrows(RutaInvalidaException.class, () -> {
      ValidadorPeorContrasenia validador = new ValidadorPeorContrasenia();
      validador.setNombreArchivo("archivomalo.txt");
      validador.validarPosiblePeorContrasenia("perotambienmegustasuperman");
    });}


}
