package ar.edu.utn.frba.dds.testMedioComunicacion;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.establecimiento.Establecimiento;
import ar.edu.utn.frba.dds.domain.establecimiento.TipoEstablecimiento;
import ar.edu.utn.frba.dds.domain.localizacion.Localizacion;
import ar.edu.utn.frba.dds.domain.localizacion.division.Division;
import ar.edu.utn.frba.dds.domain.localizacion.division.TipoDivision;
import ar.edu.utn.frba.dds.domain.medioComunicacion.MedioComunicacion;
import ar.edu.utn.frba.dds.domain.medioComunicacion.MedioComunicacionAdapter;
import ar.edu.utn.frba.dds.domain.medioComunicacion.MedioEmail;
import ar.edu.utn.frba.dds.domain.medioComunicacion.WhatsApp;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioComunidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioEstablecimientos;
import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import ar.edu.utn.frba.dds.domain.servicio.TipoServicio;
import ar.edu.utn.frba.dds.domain.usuario.RangoHorario;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import ar.edu.utn.frba.dds.exceptions.SeEnvioEmailException;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import main.Bootstrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedioComunicacionTest implements WithSimplePersistenceUnit {

  MedioEmail medioEmail;
  RangoHorario horario;
/*
  @BeforeEach
  public void setUp() {
    new Bootstrap().run();
    medioEmail = new MedioEmail();
    WhatsApp medioWhatsapp = new WhatsApp();

////    establecimientoDeLa1 = new Establecimiento("establecimientoDeLa1", TipoEstablecimiento.SUCURSAL,
////        localizacion);
////    establecimientoDeLa2 = new Establecimiento("establecimientoDeLa1", TipoEstablecimiento.SUCURSAL,
////        localizacion2);
////    servicioDeLa1 = new Servicio(TipoServicio.BAÑO);
////    servicioDeLa2 = new Servicio(TipoServicio.BAÑO);
////    luki = new Usuario("usuario1", "elmascapodelmundo");
////    lucho = new Usuario("usuario2", "elmascapodelmundo");
////    List<Usuario> miembros = new ArrayList<>(Arrays.asList(luki, lucho));
////    List<Usuario> administradores = new ArrayList<>(Arrays.asList(luki));
////    List<Servicio> servicios = new ArrayList<>(Arrays.asList(servicioDeLa1, servicioDeLa2));
////
////    rockandrolleros =new Comunidad(miembros, administradores, servicios, "RockAndRolleros"); // una comunidad
////    RepositorioComunidad.getInstancia().aniadirComunidad(rockandrolleros);
////
////    incidente1dela1 = new Incidente("Se tapo el baño", rockandrolleros);
////    incidente2dela1 = new Incidente("No hay agua", rockandrolleros);
////    incidente1dela2 = new Incidente("no anda la cadena", rockandrolleros);
////
////    establecimientoDeLa1.darAltaServicio(servicioDeLa1);
////    establecimientoDeLa2.darAltaServicio(servicioDeLa2);
////
////    division = new Division("Monte Grande", TipoDivision.MUNICIPIO);
//    localizacion = new Localizacion("Buenos Aires", division);
//    localizacion.setLatitud(100.00);
//    localizacion.setLongitud(100.00);
//    localizacion2 = new Localizacion("Buenos Aires", division);
//    localizacion2.setLatitud(100.00);
//    localizacion2.setLongitud(100.00);
////    medioEmail = new MedioEmail();
////    medioWhatsapp = new WhatsApp();
////    RepositorioEstablecimientos.getInstancia().aniadirEstablecimiento(establecimientoDeLa1);
////    RepositorioEstablecimientos.getInstancia().aniadirEstablecimiento(establecimientoDeLa2);
  }
  @Test
  public void seEncuentraServiciosCErcanosYMandaEmail() {

      horario = new RangoHorario(00,24);
    MedioComunicacion servicioMock = Mockito.mock(MedioComunicacion.class);
    List<Usuario> usuarios = entityManager().createQuery("from Usuario", Usuario.class).getResultList();
    Usuario luki = usuarios.get(0);
    luki.setMedioComunicacion(medioEmail);
    luki.agregarHorarioNotificacion(horario);
    List<Establecimiento> establecimientos = entityManager().createQuery("from Establecimiento ", Establecimiento.class).getResultList();
    Mockito
        .when(servicioMock.notificarServicioCercano(luki, new ArrayList<Servicio>()))
        .thenReturn("OK");
    Assertions.assertNotNull(establecimientos.get(0).estaCerca(luki));

  }
*/
}


