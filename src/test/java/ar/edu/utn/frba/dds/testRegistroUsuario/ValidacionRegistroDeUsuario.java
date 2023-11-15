package ar.edu.utn.frba.dds.testRegistroUsuario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.domain.repositorios.RepositorioDeUsuarios;
import ar.edu.utn.frba.dds.domain.validadores.ValidadorPeorContrasenia;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import org.junit.jupiter.api.*;

public class ValidacionRegistroDeUsuario {

  private final RepositorioDeUsuarios repousers = new RepositorioDeUsuarios();

  @BeforeEach
  public void comienzo(){
    ValidadorPeorContrasenia.getINSTANCE().setNombreArchivo("contraseniasPeligrosas.txt");
  }
  @Test
  public void unUsuarioSeRegistraSiSeValidaCorrectamenteLaContrasenia() {
    Usuario usuario = new Usuario("soybatman", "elmascapodelmundo");
    repousers.aniadirUsuario(usuario);
    Assertions.assertTrue(repousers.getUsuariosDeLaPlataforma().stream().anyMatch(  u -> u.getNombreUsuario().equals("soybatman") ));
}

  /*@Test // Esta forma es la correcta? --> Preguntar a Rolli
  public void unUsuarioNoSeRegistraSiNoSeValidaCorrectamenteLaContrasenia() {
    try{
      Usuario usuario = new Usuario("soybatman", "123456789");
      repousers.aniadirUsuario(usuario);
    }catch (Exception e){
      Assertions.assertFalse(repousers.getUsuariosDeLaPlataforma().stream().anyMatch(usuario -> usuario.getNombreUsuario().equals("soybatman") && usuario.getContrasenia().equals("123456789")));
    }
  }*/

}
