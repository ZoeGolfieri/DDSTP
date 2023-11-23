package controller;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioComunidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioDeUsuarios;
import ar.edu.utn.frba.dds.domain.servicio.EstadoIncidente;
import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComunidadesController {
  public ModelAndView  listar(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    Integer id = request.session().attribute("user_id");
    Usuario usuario = RepositorioDeUsuarios.getINSTANCE().buscarPorId(id);
    List<Comunidad> comunidades = RepositorioComunidad.getInstancia().comunidadesALasQuePertenece(usuario);

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      // Convierte la lista a JSON
      String comunidadesJson = objectMapper.writeValueAsString(comunidades);
      modelo.put("comunidades", comunidadesJson);
      return new ModelAndView(modelo,"comunidades.html.hbs");
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }
  public ModelAndView listarIncidentes(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    request.queryParams("estadoIncidentes");

    Integer id = request.session().attribute("user_id");
    Usuario usuario = RepositorioDeUsuarios.getINSTANCE().buscarPorId(id);
    List<Comunidad> comunidades = RepositorioComunidad.getInstancia().comunidadesALasQuePertenece(usuario);
    List<List<Incidente>> listaincidentes = comunidades.stream().map(comunidad -> {
      if(request.queryParams("estadoIncidentes") != null) {
        EstadoIncidente estado = EstadoIncidente.valueOf(request.queryParams("estadoIncidentes"));
        return comunidad.consultarIncidentesPorEstado(estado);
      }
      return comunidad.incidentesReportados();
    }).toList();

    if(request.queryParams("estadoIncidentes") != null) {
      String estadoIncidentes = request.queryParams("estadoIncidentes");
      if (estadoIncidentes.equals("ACTIVO")) {
        modelo.put("activo", true);
        modelo.put("cerrado", false);
        modelo.put("todos", false);
      } else if (estadoIncidentes.equals("RESUELTO")) {
        modelo.put("cerrado", true);
        modelo.put("activo", false);
        modelo.put("todos", false);
      } }
      else {
      modelo.put("activo", false);
      modelo.put("cerrado", false);
      modelo.put("todos", true);
    }

    modelo.put("incidentes", listaincidentes);
    modelo.put("path","comunidades");
    return new ModelAndView(modelo, "incidentes.html.hbs");
  }

//  public ModelAndView listarSugerenciaRevisionDeIncidentes(Request request, Response response) {
//    Map<String, Object> modelo = new HashMap<>();
//    request.queryParams("estadoIncidentes");
//
//    Integer id = request.session().attribute("user_id");
//    Usuario usuario = RepositorioDeUsuarios.getINSTANCE().buscarPorId(id);
//    List<Comunidad> comunidades = RepositorioComunidad.getInstancia().comunidadesALasQuePertenece(usuario);
//    List<List<Incidente>> listaincidentes = comunidades.stream().map(comunidad -> {
//      if(request.queryParams("estadoIncidentes") != null) {
//        EstadoIncidente estado = EstadoIncidente.valueOf(request.queryParams("estadoIncidentes"));
//        return comunidad.
//      }
//      return comunidad.incidentesReportados();
//    }).toList();
//    modelo.put("incidentes", listaincidentes);
//    modelo.put("path","sugerencias");
//    return new ModelAndView(modelo, "sugerencia_revision_incidentes.html.hbs");
//  }

}
