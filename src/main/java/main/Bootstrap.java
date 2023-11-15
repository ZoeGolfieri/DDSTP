package main;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.entidad.Entidad;
import ar.edu.utn.frba.dds.domain.establecimiento.Establecimiento;
import ar.edu.utn.frba.dds.domain.establecimiento.TipoEstablecimiento;
import ar.edu.utn.frba.dds.domain.localizacion.Localizacion;
import ar.edu.utn.frba.dds.domain.localizacion.Provincia;
import ar.edu.utn.frba.dds.domain.localizacion.division.Division;
import ar.edu.utn.frba.dds.domain.localizacion.division.TipoDivision;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioComunidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioDeEntidades;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioDeUsuarios;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioEstablecimientos;
import ar.edu.utn.frba.dds.domain.servicio.EstadoIncidente;
import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import ar.edu.utn.frba.dds.domain.servicio.TipoServicio;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import com.google.common.util.concurrent.Service;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Ejecutar antes de levantar el servidor por primera vez
 *
 * @author flbulgarelli
 */
public class Bootstrap implements WithSimplePersistenceUnit {

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void run() {
    withTransaction(() -> {

      /*------------DECLARO LOCALIZACION-----------------------*/
      Division division = new Division("CABA", TipoDivision.MUNICIPIO);
      Localizacion localizacion = new Localizacion("Buenos Aires", division, 10.00, 11.00);

      /*------------DECLARO USUARIOS---------------------------*/
      Usuario usuarioAdmin = new Usuario("usuario1","elmascapodelmundo");
      Usuario usuarioPrueba2 = new Usuario("usuario2","elmascapodelmundo");
      Usuario usuarioPrueba3 = new Usuario("usuario3","elmascapodelmundo");

      Usuario luki = new Usuario("usuarioX", "elmascapodelmundo");
      Usuario lucho = new Usuario("usuarioY", "elmascapodelmundo");

      /*------------DECLARO ENTIDADES--------------------------*/
      Entidad entidad = new Entidad();
      entidad.setearNombre("EntidadGenerica");
      RepositorioDeEntidades.getInstancia().aniadirEntidad(entidad);

      Entidad entidad1 = new Entidad();
      entidad1.setearNombre("SuperHeroes");
      Entidad entidad2 = new Entidad();
      entidad2.setearNombre("SuperVillanos");
      RepositorioDeEntidades.getInstancia().aniadirEntidad(entidad1);
      RepositorioDeEntidades.getInstancia().aniadirEntidad(entidad2);

      /*------------DECLARO SERVICIOS-------------------*/
      Servicio servicio1 = new Servicio(TipoServicio.BAÑO);
      Servicio servicio2 = new Servicio(TipoServicio.ELEVACION);
      Servicio servicio3 = new Servicio(TipoServicio.BAÑO);

      Servicio servicioDeLa1 = new Servicio(TipoServicio.BAÑO);
      Servicio servicioDeLa2 = new Servicio(TipoServicio.BAÑO);
      Servicio banioDeEstacion = new Servicio(TipoServicio.BAÑO);
      Servicio elevadorDeEstacion = new Servicio(TipoServicio.ELEVACION);

      /*------------DECLARO ESTABLECIMIENTOS------------*/

      Establecimiento establecimiento = new Establecimiento("parada de tren",TipoEstablecimiento.ESTACION, localizacion);
      RepositorioEstablecimientos.getInstancia().aniadirEstablecimiento(establecimiento);
      entidad.aniadirEstablecimiento(establecimiento);
      establecimiento.darAltaServicio(servicio1);
      establecimiento.darAltaServicio(servicio2);
      establecimiento.darAltaServicio(servicio3);

      Establecimiento paradaDeSubte = new Establecimiento("Parada de Subte X",
          TipoEstablecimiento.ESTACION, localizacion);
      Establecimiento establecimientoDeLa1 = new Establecimiento("establecimientoDeLa1",
          TipoEstablecimiento.SUCURSAL, localizacion);
      Establecimiento establecimientoDeLa2 = new Establecimiento("establecimientoDeLa2",
          TipoEstablecimiento.SUCURSAL, localizacion);
      RepositorioEstablecimientos.getInstancia().aniadirEstablecimiento(establecimientoDeLa1);
      RepositorioEstablecimientos.getInstancia().aniadirEstablecimiento(establecimientoDeLa2);
      entidad1.aniadirEstablecimiento(establecimientoDeLa1);
      entidad1.aniadirEstablecimiento(paradaDeSubte);
      entidad2.aniadirEstablecimiento(establecimientoDeLa2);

      establecimientoDeLa1.darAltaServicio(servicioDeLa1);
      paradaDeSubte.darAltaServicio(banioDeEstacion);
      paradaDeSubte.darAltaServicio(elevadorDeEstacion);
      establecimientoDeLa2.darAltaServicio(servicioDeLa2);

      /*-----------CREO COMUNIDADES-----------------*/
      List<Usuario> usuariosMiembros = new ArrayList<>();
      usuariosMiembros.add(usuarioPrueba2);
      usuariosMiembros.add(usuarioPrueba3);
      usuariosMiembros.add(usuarioAdmin);
      List<Usuario> usuariosAdmin = new ArrayList<>();
      usuariosAdmin.add(usuarioAdmin);
      List<Servicio> servciosDeInteres = new ArrayList<>();
      servciosDeInteres.add(servicio1);
      servciosDeInteres.add(servicio2);
      servciosDeInteres.add(servicio3);
      Comunidad com1 = new Comunidad(usuariosMiembros, usuariosAdmin, servciosDeInteres,"comunidad prueba 1");
      RepositorioComunidad.getInstancia().aniadirComunidad(com1);

      List<Usuario> miembros = new ArrayList<>(Arrays.asList(luki, lucho));
      List<Usuario> administradores = new ArrayList<>(Arrays.asList(luki));
      List<Servicio> servicios = new ArrayList<>(Arrays
          .asList(servicioDeLa1, servicioDeLa2, banioDeEstacion, elevadorDeEstacion));
      Comunidad rockandrolleros = new Comunidad(miembros, administradores, servicios,"RockAndRolleros");
      RepositorioComunidad.getInstancia().aniadirComunidad(rockandrolleros);

      /*-----------REPORTO INCIDENTES----------------*/

      servicio1.informarNoFuncionamiento("no hay agua");
      servicio2.informarNoFuncionamiento("rotas las escaleras");
      servicio3.informarNoFuncionamiento("se tapo el banio");

      servicio1.getIncidentes().get(0).setEstado(EstadoIncidente.RESUELTO);
      servicio1.getIncidentes().get(0).setHorarioCierre(LocalDateTime.now().plusHours(2));

      servicioDeLa1.informarNoFuncionamiento("Se tapo el baño");
      servicioDeLa1.informarNoFuncionamiento("No hay agua");
      banioDeEstacion.informarNoFuncionamiento("Se tapo");
      servicioDeLa2.informarNoFuncionamiento("No anda la cadena");

      servicioDeLa1.getIncidentes().get(0).setEstado(EstadoIncidente.RESUELTO);
      servicioDeLa1.getIncidentes().get(1).setEstado(EstadoIncidente.RESUELTO);
      servicioDeLa2.getIncidentes().get(0).setEstado(EstadoIncidente.RESUELTO);

      servicioDeLa1.getIncidentes().get(0).setHorarioCierre(LocalDateTime.now().plusHours(5));
      servicioDeLa1.getIncidentes().get(1).setHorarioCierre(LocalDateTime.now().plusHours(5));
      servicioDeLa2.getIncidentes().get(0).setHorarioCierre(LocalDateTime.now().plusHours(10));

      /*---------------PERSISTO LAS COSAS EN ORDEN (INCIDENTE->SERVICIO->ESTABLECIMIENTO->ENTIDAD)----*/

      persist(usuarioAdmin);
      persist(usuarioPrueba2);
      persist(usuarioPrueba3);
      persist(com1);
      persist(servicio1.getIncidentes().get(0));
      persist(servicio2.getIncidentes().get(0));
      persist(servicio3.getIncidentes().get(0));
      persist(servicio1);
      persist(servicio2);
      persist(servicio3);
      persist(establecimiento);
      persist(entidad);

      persist(luki);
      persist(lucho);
      persist(rockandrolleros);
      persist(servicioDeLa1.getIncidentes().get(0));
      persist(servicioDeLa1.getIncidentes().get(1));
      persist(banioDeEstacion.getIncidentes().get(0));
      persist(servicioDeLa2.getIncidentes().get(0));
      persist(servicioDeLa1);
      persist(servicioDeLa2);
      persist(banioDeEstacion);
      persist(elevadorDeEstacion);
      persist(establecimientoDeLa1);
      persist(paradaDeSubte);
      persist(establecimientoDeLa2);
      persist(entidad1);
      persist(entidad2);
    });
  }

}