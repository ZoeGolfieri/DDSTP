package ar.edu.utn.frba.dds.testRegistroUsuario;


import ar.edu.utn.frba.dds.domain.repositorios.RepositorioEmpresas;
import ar.edu.utn.frba.dds.exceptions.RutaInvalidaException;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidacionRegistroDeEmpresasTest {

  RepositorioEmpresas repouser = new RepositorioEmpresas();

  @Test
  public void SiElCSVTiene3EmpresasSeRegistranTodas() {

    repouser.registrarEmpresas("entidadesPrestadoras.csv");

    // Verifica que se registraron tres empresas, cantidad hardcodeada del csv
    Assertions.assertEquals(3, repouser.getEmpresasUsuarias().size());
  }


  @Test
  public void LaRutaProporcionadaNoEsCorrectaYLanzaExcepcion() {    

    // Intentar registrar las empresas y esperar la excepción
    Assertions.assertThrows(RutaInvalidaException.class, () -> {
      repouser.registrarEmpresas("entidades.csv");
    });
  }

}
