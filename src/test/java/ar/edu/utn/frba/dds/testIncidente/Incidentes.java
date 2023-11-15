package ar.edu.utn.frba.dds.testIncidente;

import static ar.edu.utn.frba.dds.domain.servicio.EstadoIncidente.ACTIVO;
import static ar.edu.utn.frba.dds.domain.servicio.EstadoIncidente.RESUELTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.localizacion.Localizacion;
import ar.edu.utn.frba.dds.domain.localizacion.division.Division;
import ar.edu.utn.frba.dds.domain.localizacion.division.TipoDivision;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Incidentes {
  private Usuario usuario1,usuario2,usuario3;
  List<Usuario> miembros1,miembros2;
  List<Usuario> administradores1,administradores2;
  Servicio servicioBaño,servicioElevacion;
  List<Servicio> serviciosInteres1,serviciosInteres2;
  Comunidad comunidad1,comunidad2,comunidad3;

  @BeforeEach
  public void setUp() {
    usuario1 = new Usuario("usuario1", "elmascapodelmundo");
    usuario2 = new Usuario("usuario2", "elmascapodelmundo");
    usuario3 = new Usuario("usuario3", "elmascapodelmundo");
    miembros1 = new ArrayList<>(Arrays.asList(usuario1, usuario2));
    miembros2 = new ArrayList<>(Arrays.asList(usuario3));
    administradores1 = new ArrayList<>(Arrays.asList(usuario1));
    administradores2 = new ArrayList<>(Arrays.asList(usuario3));
    servicioBaño = new Servicio(TipoServicio.BAÑO);
    servicioElevacion = new Servicio(TipoServicio.ELEVACION);
    serviciosInteres1 = new ArrayList<>(Arrays.asList(servicioBaño, servicioElevacion));
    serviciosInteres2 = new ArrayList<>(Arrays.asList(servicioElevacion));
    comunidad1 = new Comunidad(miembros1, administradores1, serviciosInteres1, "RockAndRolleros");
    comunidad2 = new Comunidad(miembros2, administradores2, serviciosInteres2, "RockAndRolleros");
    comunidad3 = new Comunidad(miembros2, administradores2, serviciosInteres1, "RockAndRolleros");
    //ESTO NO TENDRIA QUE HACERCE AUTOMATICAMENTE AL CREAR UNA COMUNIDAD??
    RepositorioComunidad.getInstancia().aniadirComunidad(comunidad1);
    RepositorioComunidad.getInstancia().aniadirComunidad(comunidad2);
    RepositorioComunidad.getInstancia().aniadirComunidad(comunidad3);
  }


  @Test
  public void informarNoFuncionamiento() {
    servicioBaño.informarNoFuncionamiento("No hay agua");
    if (servicioBaño.getIncidentes().get(0).getHorarioApertura() == null) {
      fail("El campo 'horarioApertura' es null");
    }
    assertEquals(2,servicioBaño.getIncidentes().size());
    assertEquals(0,servicioElevacion.getIncidentes().size());
    assertEquals(comunidad1,servicioBaño.getIncidentes().get(0).getComunidad());
    assertEquals("No hay agua",servicioBaño.getIncidentes().get(0).getObservaciones());
    assertEquals(ACTIVO,servicioBaño.getIncidentes().get(0).getEstado());
  }

  @Test
  public void cerrarIncidenteParaComunidad() {
    servicioBaño.informarNoFuncionamiento("No hay agua");
    servicioBaño.getIncidentes().get(0).cerrarIncidente();
    if (servicioBaño.getIncidentes().get(0).getHorarioCierre() == null) {
      fail("El campo 'HorarioCierre' es null");
    }
    assertEquals(RESUELTO,servicioBaño.getIncidentes().get(0).getEstado());
    assertEquals(ACTIVO,servicioBaño.getIncidentes().get(1).getEstado());
  }

  @Test
  public void consultarIncidentesPorEstadoACTIVO() {
    servicioBaño.informarNoFuncionamiento("No hay agua");
    servicioElevacion.informarNoFuncionamiento("No funciona");
    servicioBaño.getIncidentes().get(0).cerrarIncidente();

    assertEquals(1,comunidad1.consultarIncidentesPorEstado(ACTIVO).size());
    assertEquals(2,comunidad3.consultarIncidentesPorEstado(ACTIVO).size());
    assertEquals(1,comunidad2.consultarIncidentesPorEstado(ACTIVO).size());
  }

  @Test
  public void consultarIncidentesPorEstadoRESUELTO() {
    servicioBaño.informarNoFuncionamiento("No hay agua");
    servicioElevacion.informarNoFuncionamiento("No funciona");
    servicioBaño.getIncidentes().get(0).cerrarIncidente();
    servicioBaño.getIncidentes().get(1).cerrarIncidente();
    servicioElevacion.getIncidentes().get(1).cerrarIncidente();
    servicioElevacion.getIncidentes().get(2).cerrarIncidente();
    assertEquals(1,comunidad1.consultarIncidentesPorEstado(RESUELTO).size());
    assertEquals(2,comunidad3.consultarIncidentesPorEstado(RESUELTO).size());
    assertEquals(1,comunidad2.consultarIncidentesPorEstado(RESUELTO).size());
  }

  @Test
  public void incidentesReportadosComunidad() {
    servicioBaño.informarNoFuncionamiento("No hay agua");
    servicioElevacion.informarNoFuncionamiento("No funciona");
    servicioBaño.getIncidentes().get(0).cerrarIncidente();
    servicioBaño.getIncidentes().get(1).cerrarIncidente();
    servicioElevacion.getIncidentes().get(1).cerrarIncidente();
    servicioElevacion.getIncidentes().get(2).cerrarIncidente();

    assertEquals(2,comunidad1.incidentesReportados().size());
    assertEquals(1,comunidad2.incidentesReportados().size());
    assertEquals(2,comunidad3.incidentesReportados().size());
  }

}
