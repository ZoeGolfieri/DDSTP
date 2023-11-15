package controller;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioComunidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioDeEntidades;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioDeUsuarios;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioEstablecimientos;
import ar.edu.utn.frba.dds.domain.servicio.EstadoIncidente;
import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.*;
import java.util.stream.Collectors;

public class DemoController implements WithSimplePersistenceUnit {

  public ModelAndView home(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    request.queryParams("estadoIncidente");

    // Descomentar cuando el login este hecho
    //Integer id = request.session().attribute("user_id");
    //Usuario usuario = RepositorioDeUsuarios.getINSTANCE().findById(id);
    //List<Comunidad> comunidades = RepositorioComunidad.getInstancia().comunidadesALasQuePertenece(usuario);

    List<Comunidad> comunidades = RepositorioComunidad.getInstancia().obtenerTodos(); // para probar
    List<List<Incidente>> listaincidentes = comunidades.stream().map(comunidad -> {

          // Descomentar cuando el login este hecho
          //EstadoIncidente estado = EstadoIncidente.valueOf(request.queryParams("estadoIncidentes"));;
          //return comunidad.consultarIncidentesPorEstado(estado);

        return comunidad.incidentesReportados(); // para probar
    }).toList();
    modelo.put("incidentes", listaincidentes);
    return new ModelAndView(modelo, "index.html.hbs");
  }

  public ModelAndView establecimientos(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("establecimientos", RepositorioEstablecimientos.getInstancia().obtenerTodos());
    return new ModelAndView(modelo, "index.html.hbs"); // cambiar esto del index
  }

  public ModelAndView servicios(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
//    modelo.put("establecimientos", RepositorioEstablecimientos.getInstancia().obtenerTodos());
    return new ModelAndView(modelo, "servicios_establecimiento.html.hbs"); // cambiar esto del index
  }

  public ModelAndView rankings(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("rankingCantidad", new ArrayList<>(RepositorioDeEntidades.getInstancia().calcularRankingCantidad()));
    modelo.put("rankingCierre", new ArrayList<>(RepositorioDeEntidades.getInstancia().calcularRankingCierre()));
    return new ModelAndView(modelo, "rankings.html.hbs");
  }
}
