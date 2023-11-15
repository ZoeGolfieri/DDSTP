package ar.edu.utn.frba.dds.testComunidad;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.localizacion.Localizacion;
import ar.edu.utn.frba.dds.domain.localizacion.division.Division;
import ar.edu.utn.frba.dds.domain.localizacion.division.TipoDivision;
import ar.edu.utn.frba.dds.domain.medioComunicacion.MedioComunicacion;
import ar.edu.utn.frba.dds.domain.medioComunicacion.MedioEmail;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioComunidad;
import ar.edu.utn.frba.dds.domain.servicio.EstadoIncidente;
import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import ar.edu.utn.frba.dds.domain.servicio.TipoServicio;
import ar.edu.utn.frba.dds.domain.usuario.RangoHorario;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import ar.edu.utn.frba.dds.exceptions.SeEnvioEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidacionComunidad {
  private Servicio servicioDeLa1;
  private Servicio servicioDeLa2;
  private Incidente incidente1dela1;
  private Incidente incidente2dela1;
  private Incidente incidente1dela2;
  private Comunidad rockandrolleros;
  private Usuario luki;
  private Usuario lucho;
  private Localizacion localizacion;
  private Division division;
  private RangoHorario horario;
  private List<RangoHorario> horariosNotificacion = new ArrayList<>();
  private MedioEmail medioEmail;
  @BeforeEach
  public void setUp() {
    servicioDeLa1 = new Servicio(TipoServicio.BAÑO);
    servicioDeLa2 = new Servicio(TipoServicio.BAÑO);
    luki = new Usuario("usuario1", "elmascapodelmundo");
    lucho = new Usuario("usuario2", "elmascapodelmundo");
    List<Usuario> miembros = new ArrayList<>(Arrays.asList(luki, lucho));
    List<Usuario> administradores = new ArrayList<>(Arrays.asList(luki));
    List<Servicio> servicios = new ArrayList<>(Arrays.asList(servicioDeLa1, servicioDeLa2));
    rockandrolleros =new Comunidad(miembros, administradores, servicios, "RockAndRolleros");
    incidente1dela1 = new Incidente("Se tapo el baño", rockandrolleros);
    incidente2dela1 = new Incidente("No hay agua", rockandrolleros);
    incidente1dela2 = new Incidente("no anda la cadena", rockandrolleros);
    division = new Division("Monte Grande", TipoDivision.MUNICIPIO);
    localizacion = new Localizacion("Buenos Aires", division);
    horario = new RangoHorario(00,24);
    horariosNotificacion = new ArrayList<>(Arrays.asList(horario));
    medioEmail = new MedioEmail();
  }

  @Test
  public void notificaIncidente() {
    luki.setLocalizacion_actual(localizacion);
    luki.setHorariosNotificacion(horariosNotificacion);
    luki.setMedioComunicacion(medioEmail);
    luki.setEmail("luki@gmail.com");
    assertThrows(SeEnvioEmailException.class, ()->{rockandrolleros.reportarIncidente(incidente1dela1);}, "" +
        "Error al enviar el correo electrónico: luki@gmail.com");
    //assertEquals(medioEmail.notificarIncidente(luki, incidente1dela1), localizacion);
  }

  @Test
  public void consultarIncidentesPorEstadoActivo(){
    servicioDeLa1.informarNoFuncionamiento("no funciona el baño");
    assertEquals(rockandrolleros.consultarIncidentesPorEstado(EstadoIncidente.ACTIVO), servicioDeLa1.getIncidentes());
  }

  @Test
  public void consultarIncidentesPorEstadoResuelto(){
    servicioDeLa1.informarNoFuncionamiento("no funciona el baño");
    servicioDeLa1.getIncidentes().forEach(incidente1 -> incidente1.cerrarIncidente());
    assertEquals(rockandrolleros.consultarIncidentesPorEstado(EstadoIncidente.RESUELTO), servicioDeLa1.getIncidentes());
  }

  @Test
  public void incidentesReportados(){
    servicioDeLa1.informarNoFuncionamiento("no funciona el baño");
    assertEquals(rockandrolleros.incidentesReportados(), servicioDeLa1.getIncidentes());
  }

  @Test
  public void agregarComunidad(){
    RepositorioComunidad.getInstancia().aniadirComunidad(rockandrolleros);
    assertEquals(RepositorioComunidad.getInstancia().getComunidades().size(),1 );
  }

  @Test
  public void esAdmin(){
    RepositorioComunidad.getInstancia().aniadirComunidad(rockandrolleros);
    List<Comunidad> comunidades = RepositorioComunidad.getInstancia().comunidadesALasQuePertenece(luki);
   // List<Comunidad> comunidadesAdmin = comunidades.stream().filter(comunidad-> comunidad.getAdministradores().contains(luki)).toList();
    assertEquals(comunidades.size(),1 );
  }
}
