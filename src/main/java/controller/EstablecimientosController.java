package controller;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.establecimiento.Establecimiento;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioComunidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioDeUsuarios;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioEstablecimientos;
import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EstablecimientosController implements WithSimplePersistenceUnit {

  public ModelAndView establecimientos(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    List<Establecimiento> establecimientos = RepositorioEstablecimientos.getInstancia().obtenerTodos();
    modelo.put("establecimientos", establecimientos);
    modelo.put("path","establecimientos");
    return new ModelAndView(modelo, "establecimientos.html.hbs"); // cambiar esto del index
  }

  public ModelAndView serviciosCercanos(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
//    List<Establecimiento> establecimientos = RepositorioEstablecimientos.getInstancia().obtenerTodos();
//    List<Servicio> serviciosSugeridos = establecimientos.stream().map(establecimiento -> {
//      Integer id = request.session().attribute("user_id");
//      Usuario usuario = RepositorioDeUsuarios.getINSTANCE().buscarPorId(id);
//      List<Servicio> servicios = establecimiento.estaCerca(usuario);
//      return servicios;
//    }).flatMap(Collection::stream).toList();
//    System.out.println(serviciosSugeridos);
//    modelo.put("servicios", serviciosSugeridos);
    modelo.put("path","sugerencias");
    return new ModelAndView(modelo, "sugerencia_revision_incidentes.html.hbs"); // cambiar esto del index
  }

  public ModelAndView servicios(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    request.params("id");
    Establecimiento establecimiento = RepositorioEstablecimientos.getInstancia().buscar(Integer.parseInt(request.params("id")));
    modelo.put("establecimiento", establecimiento);
    System.out.println(establecimiento);
    modelo.put("path","establecimientos");
    return new ModelAndView(modelo, "servicios_establecimiento.html.hbs"); // cambiar esto del index
  }

  public ModelAndView formularioAperturaIncidente(Request request, Response response){
    Map<String, Object> modelo = new HashMap<>();
    Establecimiento establecimiento = RepositorioEstablecimientos.getInstancia().buscar(Integer.parseInt(request.params("id_est")));
    Integer servicioNuevo = establecimiento.getServicios().stream().filter(servicio -> servicio.getId_servicio().equals(Integer.parseInt(request.params("id_serv"))))
        .toList()
        .get(0).getId_servicio();
    modelo.put("id_servicio",servicioNuevo);
    modelo.put("path",request.uri());
//    Integer id = request.session().attribute("user_id");
//    Usuario usuario = RepositorioDeUsuarios.getINSTANCE().buscarPorId(id);
//    List<Comunidad> comunidades = RepositorioComunidad.getInstancia().comunidadesALasQuePertenece(usuario);
//    modelo.put("comunidades",comunidades);
    return new ModelAndView(modelo, "apertura_incidente.hbs");
  }

  public Void aperturaIncidente(Request request, Response response){

    request.params("id_serv");
    List <Establecimiento> establecimientos = RepositorioEstablecimientos.getInstancia().obtenerTodos();
    RepositorioEstablecimientos.getInstancia().reportarIncidente(Integer.parseInt(request.params("id_est")),Integer.parseInt(request.params("id_serv")),(request.queryParams("observaciones")));
    response.redirect("/establecimientos");
    return null;
  }
}
