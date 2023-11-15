package ar.edu.utn.frba.dds.testIncidente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioComunidad;
import ar.edu.utn.frba.dds.domain.medioComunicacion.MedioComunicacion;
import ar.edu.utn.frba.dds.domain.medioComunicacion.MedioEmail;
import ar.edu.utn.frba.dds.domain.medioComunicacion.WhatsApp;
import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import ar.edu.utn.frba.dds.domain.servicio.TipoServicio;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import ar.edu.utn.frba.dds.exceptions.SeEnvioEmailException;
import ar.edu.utn.frba.dds.exceptions.SeEnvioWhatsappException;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidacionEnvioIncidentes {
  Usuario usuario = new Usuario("usuario1", "elmascapodelmundo");
  Usuario usuario2 = new Usuario("usuario2", "elmascapodelmundo");
  List<Usuario> miembros = new ArrayList<>(Arrays.asList(usuario, usuario2));
  List<Usuario> administradores = new ArrayList<>(Arrays.asList(usuario));
  Servicio servicio = new Servicio(TipoServicio.BAÑO);
  List<Servicio> servicios = new ArrayList<>(Arrays.asList(servicio));

  MedioComunicacion whatsApp = new WhatsApp();
  MedioComunicacion email = new MedioEmail();
  List<MedioComunicacion> mediosComunicacion = new ArrayList<>(Arrays.asList(whatsApp,email));
  Comunidad comunidad = new Comunidad(miembros, administradores, servicios, "RockAndRolleros");
  List<Comunidad> comunidades = new ArrayList<>(Arrays.asList(comunidad));
  boolean repositorioComunidad = RepositorioComunidad.getInstancia().getComunidades().add(comunidad);


//  @Test
//  public void reportarUnIncidenteAvisaALosUsuariosWhatsApp() {
//    usuario.setServiciosInteres(servicios);
//    usuario2.setServiciosInteres(servicios);
//
//    assertEquals(comunidad.usuarioEsParte(usuario), true);
////    assertEquals(usuario.comunidadesDelUsuario(), comunidades);
//
////    usuario2.suscribirseMedioComunicacion(whatsApp);
//    //usuario2.suscribirseMedioComunicacion(email);
//    List<Usuario> suscriptoresMedios = new ArrayList<>(Arrays.asList(usuario2));
////    assertEquals(whatsApp.getUsuariosSuscriptos(), suscriptoresMedios);
//    //assertEquals(email.getUsuariosSuscriptos(), suscriptoresMedios);
//
//    assertThrows(SeEnvioWhatsappException.class, () -> {
////      Incidente incidente = usuario.informarNoFuncionamiento(servicio, "Inodoro roto");
////      List<Incidente> incidentes = new ArrayList<>(Arrays.asList(incidente));
//
////      assertEquals(servicio.getIncidentes(), incidentes);
////      assertEquals(incidentes, comunidad.getIncidentesReportados());
//
//    });
//  }

//  @Test
//  public void reportarUnIncidenteAvisaALosUsuariosEmail() {
//    usuario.setServiciosInteres(servicios);
//    usuario2.setServiciosInteres(servicios);
//
//    assertEquals(comunidad.usuarioEsParte(usuario), true);
//    assertEquals(usuario.comunidadesDelUsuario(), comunidades);
//
//    usuario2.suscribirseMedioComunicacion(email);
//    List<Usuario> suscriptoresMedios = new ArrayList<>(Arrays.asList(usuario2));
//    assertEquals(email.getUsuariosSuscriptos(), suscriptoresMedios);
//
//    assertThrows(SeEnvioEmailException.class, () -> {
//      Incidente incidente = usuario.informarNoFuncionamiento(servicio, "Inodoro roto");
//      List<Incidente> incidentes = new ArrayList<>(Arrays.asList(incidente));
//
//      assertEquals(servicio.getIncidentes(), incidentes);
//      assertEquals(incidentes, comunidad.getIncidentesReportados());
//
//    });
//  }

  }
