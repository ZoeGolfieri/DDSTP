package controller;

import ar.edu.utn.frba.dds.domain.entidad.Entidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioDeEntidades;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RankingsController implements WithSimplePersistenceUnit {
  public ModelAndView rankings(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    request.session().attribute("path","rankings");
    modelo.put("posicionesRankingCantidad", new ArrayList<>(RepositorioDeEntidades.getInstancia().cantidadRankingCantidad()));
    modelo.put("posicionesRankingCierre", new ArrayList<>(RepositorioDeEntidades.getInstancia().cantidadRankingCierre()));
    modelo.put("rankingCantidad", new ArrayList<>(RepositorioDeEntidades.getInstancia().calcularRankingCantidad().stream()
        .map(Entidad::getNombreEntidad).toList()));
    modelo.put("rankingCierre", new ArrayList<>(RepositorioDeEntidades.getInstancia().calcularRankingCierre().stream()
        .map(Entidad::getNombreEntidad).toList()));
    modelo.put("cantidades", new ArrayList<>(RepositorioDeEntidades.getInstancia().calcularRankingCantidad()
        .stream().map(Entidad::cantidadIncidentesEnUnaSemana).toList()));
    modelo.put("promedios", new ArrayList<>(RepositorioDeEntidades.getInstancia().calcularRankingCierre()
        .stream().map(Entidad::promedioDeCierreIncidente).toList()));
    modelo.put("path","rankings");
    return new ModelAndView(modelo, "rankings.html.hbs");
  }
}
