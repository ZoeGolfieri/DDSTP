package ar.edu.utn.frba.dds.rankingsTests;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.Ranking.RankingPorCantidad;
import ar.edu.utn.frba.dds.domain.Ranking.RankingPorPromedioCierre;
import ar.edu.utn.frba.dds.domain.entidad.Entidad;
import ar.edu.utn.frba.dds.domain.establecimiento.Establecimiento;
import ar.edu.utn.frba.dds.domain.establecimiento.TipoEstablecimiento;
import ar.edu.utn.frba.dds.domain.localizacion.Localizacion;
import ar.edu.utn.frba.dds.domain.localizacion.division.Division;
import ar.edu.utn.frba.dds.domain.localizacion.division.TipoDivision;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioDeEntidades;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioComunidad;
import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import ar.edu.utn.frba.dds.domain.servicio.TipoServicio;
import ar.edu.utn.frba.dds.domain.servicioLocalizacion.ServicioLocalizacion;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import ar.edu.utn.frba.dds.exceptions.RutaInvalidaException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RankingTests {
  private Entidad entidad1, entidad2;
  private Establecimiento establecimientoDeLa1, establecimientoDeLa2;
  private Servicio servicioDeLa1, servicioDeLa2;
  private Usuario luki, lucho;
  private Comunidad rockandrolleros;
  private Localizacion localizacion;
  private Localizacion localizacion2;
  RankingPorCantidad rankingCantidad;
  private Division division;
  RankingPorPromedioCierre rankingPromedio;
  @BeforeEach
  public void setUp() {
    
    entidad1 = new Entidad();
    entidad1.setearNombre("SuperHeroes");
    entidad2 = new Entidad();
    entidad2.setearNombre("SuperVillanos");
    establecimientoDeLa1 = new Establecimiento("establecimientoDeLa1", TipoEstablecimiento.SUCURSAL,
        localizacion);
    establecimientoDeLa2 = new Establecimiento("establecimientoDeLa1", TipoEstablecimiento.SUCURSAL,
        localizacion2);
    servicioDeLa1 = new Servicio(TipoServicio.BAÑO);
    servicioDeLa2 = new Servicio(TipoServicio.BAÑO);
    luki = new Usuario("usuario1", "elmascapodelmundo");
    lucho = new Usuario("usuario2", "elmascapodelmundo");
    List<Usuario> miembros = new ArrayList<>(Arrays.asList(luki, lucho));
    List<Usuario> administradores = new ArrayList<>(Arrays.asList(luki));
    List<Servicio> servicios = new ArrayList<>(Arrays.asList(servicioDeLa1, servicioDeLa2));
    rockandrolleros =new Comunidad(miembros, administradores, servicios,"RockAndRolleros");
    division = new Division("Monte Grande", TipoDivision.MUNICIPIO);
    localizacion = new Localizacion("Buenos Aires", division);
    localizacion.setLatitud(100.00);
    localizacion.setLongitud(100.00);
    localizacion2 = new Localizacion("Buenos Aires", division);
    localizacion2.setLatitud(90.00);
    localizacion2.setLongitud(90.00);

    RepositorioComunidad.getInstancia().aniadirComunidad(rockandrolleros);

    RepositorioDeEntidades.getInstancia().aniadirEntidad(entidad1);
    RepositorioDeEntidades.getInstancia().aniadirEntidad(entidad2);

    entidad1.aniadirEstablecimiento(establecimientoDeLa1);
    entidad2.aniadirEstablecimiento(establecimientoDeLa2);
    establecimientoDeLa1.darAltaServicio(servicioDeLa1);
    establecimientoDeLa2.darAltaServicio(servicioDeLa2);

    servicioDeLa1.informarNoFuncionamiento("Se tapo el baño");
    servicioDeLa1.informarNoFuncionamiento("No hay agua");
    servicioDeLa2.informarNoFuncionamiento("No anda la cadena");
    servicioDeLa1.informarNoFuncionamiento("cacas everywhere");

    servicioDeLa1.getIncidentes().get(0).cerrarIncidente();
    servicioDeLa1.getIncidentes().get(1).cerrarIncidente();
    servicioDeLa2.getIncidentes().get(0).cerrarIncidente();

    servicioDeLa1.getIncidentes().get(0).setHorarioCierre(LocalDateTime.now().plusHours(5));
    servicioDeLa1.getIncidentes().get(1).setHorarioCierre(LocalDateTime.now().plusHours(5));
    servicioDeLa2.getIncidentes().get(0).setHorarioCierre(LocalDateTime.now().plusHours(10));

    rankingCantidad = new RankingPorCantidad();
    rankingPromedio = new RankingPorPromedioCierre();

    RepositorioDeEntidades.getInstancia().aniadirCriterio(rankingCantidad);
    RepositorioDeEntidades.getInstancia().aniadirCriterio(rankingPromedio);
    RepositorioDeEntidades.getInstancia().generarRankings();
  }

  @AfterEach
  public void tearDown() {
    RepositorioDeEntidades.getInstancia().getEntidades().clear();
  }

  @Test
  public void seCreanLosCsv() {
    assertTrue(verificarExistenciaArchivo("RankingsCSV/rankingPorCantidad.csv"));
  }

  private boolean verificarExistenciaArchivo(String nombreArchivo) {
    File archivo = new File(nombreArchivo);
    return archivo.exists();
  }
  @Test
  public void funcionaRankingPorCantidad() {
    String rutaArchivo = "RankingsCSV/rankingPorCantidad.csv";
    List<String> contenidoLeido = leerArchivoCSV(rutaArchivo);
    //La entidad SuperHeroes tuvo 2 incidentes, supervillanos 1
    String primeraEntidad = "1, SuperHeroes";
    String segundaEntidad = "2, SuperVillanos";
    assertEquals(primeraEntidad, contenidoLeido.get(0));
    assertEquals(segundaEntidad, contenidoLeido.get(1));
    }

  @Test
  public void funcionaRankingPorRangoHorario() {
    String rutaArchivo = "RankingsCSV/rankingPorPromedio.csv";
    List<String> contenidoLeido = leerArchivoCSV(rutaArchivo);
    //Deberia terminar primero super villanos y 2do super heroes
    String primeraEntidad = "1, SuperVillanos";
    String segundaEntidad = "2, SuperHeroes";
    assertEquals(primeraEntidad, contenidoLeido.get(0));
    assertEquals(segundaEntidad, contenidoLeido.get(1));
  }
  @Test
  public void elPromedioDeCierreSeCalculaBien() {
    assertEquals(10, entidad2.promedioDeCierreIncidente());
    assertEquals(5, entidad1.promedioDeCierreIncidente());
  }
  @Test public void cantidadDeIncidentesSemanales() {
    assertEquals(3, entidad1.cantidadIncidentesEnUnaSemana());
  }
  @Test public void cantidadDeIncidentes() {
    assertEquals(3, entidad1.cantidadIncidentesEntidad());
  }

  @Test
  public void cantidadDeIncidentesDeUnServicio() {
    assertEquals(3, servicioDeLa1.getIncidentes().size());
  }

  @Test
  public void listasRankings() {
    List<String> entidadesRanking = RepositorioDeEntidades.getInstancia().calcularRankingCantidad()
        .stream().map(Entidad::getNombreEntidad).toList();
    assertEquals("SuperHeroes", entidadesRanking.get(0));
    assertEquals("SuperVillanos", entidadesRanking.get(1));
  }

  @Test
  public void listasRankingsCierre() {
    List<String> entidadesRanking = RepositorioDeEntidades.getInstancia().calcularRankingCierre()
        .stream().map(Entidad::getNombreEntidad).toList();
    assertEquals("SuperHeroes", entidadesRanking.get(1));
    assertEquals("SuperVillanos", entidadesRanking.get(0));
  }
  @Test public void elTiempoDeCierreEstaBien() {
    assertEquals(5, servicioDeLa1.getIncidentes().get(0).tiempoCierre());
  }

  private List<String> leerArchivoCSV(String rutaArchivo) {
    List<String> lineas = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
      String linea;
      while ((linea = br.readLine()) != null) {
        lineas.add(linea);
      }
    } catch (FileNotFoundException e) {
      throw new RutaInvalidaException("No se encontro el csv");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return lineas;
    }



}
