package controller;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioComunidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioIncidentes;
import ar.edu.utn.frba.dds.domain.servicio.EstadoIncidente;
import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncidentesController {

  public Void cerrar(Request request, Response response) {
    Incidente incidente = RepositorioIncidentes.getINSTANCE().buscarPorId(Integer.parseInt(request.params("id_inc")));
    try{
      RepositorioIncidentes.getINSTANCE().cerrarIncidente(incidente);
      response.redirect("/establecimientos");
      return null;
    }
    catch (Exception e) {
      response.redirect(request.uri());
      return null;
    }

  }


}
