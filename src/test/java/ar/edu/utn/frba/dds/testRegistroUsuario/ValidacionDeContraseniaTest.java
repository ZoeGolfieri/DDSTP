package ar.edu.utn.frba.dds.testRegistroUsuario;

import ar.edu.utn.frba.dds.exceptions.CaracteresRepetidosException;
import ar.edu.utn.frba.dds.exceptions.ContraseniaMuyCortaException;
import ar.edu.utn.frba.dds.exceptions.MalaContraseniaException;
import ar.edu.utn.frba.dds.exceptions.UsaCrendencialesException;
import ar.edu.utn.frba.dds.domain.validadores.ValidadorContrasenias;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ValidacionDeContraseniaTest {

  public ValidadorContrasenias validador = new ValidadorContrasenias();

  @Test
  public void unaContraseniaConCaracteresIgualesNoSirve()  {
    assertThrows(CaracteresRepetidosException.class, () -> {
      validador.validarContrasenia("batman", "muchosaaa");
    });
  }

  @Test
  public void contraseniaInvalidaPorLongitudMenorAOcho() {
    assertThrows(ContraseniaMuyCortaException.class, () -> {
      validador.validarContrasenia("batman", "abc");
    });
  }

  @Test
  public void contraseniaInvalidaPorUsoDeCredenciales() {
    assertThrows(UsaCrendencialesException.class, () -> {
      validador.validarContrasenia("soybatman", "soybatman");
    });
  }
  @Test
  public void contraseniaEsPeorContrasenia() {
    assertThrows(MalaContraseniaException.class, () -> {
      validador.validarContrasenia("soybatman", "pruebademalacontrasenia");
    });
  }

  @Test
  public void contraseniaValida() {
    validador.validarContrasenia("soybatman", "elmascapodelmundo");

  }
}


