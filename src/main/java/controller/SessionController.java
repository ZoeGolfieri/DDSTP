package controller;

import ar.edu.utn.frba.dds.domain.Comunidad.Comunidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioComunidad;
import ar.edu.utn.frba.dds.domain.repositorios.RepositorioDeUsuarios;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionController {
  Map<String, Object> modeloErrorLogin = new HashMap<>();
  Map<String, Object> modeloErrorRegistro = new HashMap<>();

  Map<String, Object> modeloErrorRestablecimiento = new HashMap<>();

  public ModelAndView mostrarLogin(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modeloErrorRegistro.remove("mantenerError");
    modeloErrorRestablecimiento.remove("mantenerError");
    if(!modeloErrorLogin.containsKey("mantenerError")) {
      modeloErrorLogin.put("error", false);
    }

    if(!modeloErrorLogin.containsKey("error"))
      return new ModelAndView(modelo, "formulario-login.html.hbs");
    else
      return new ModelAndView(modeloErrorLogin, "formulario-login.html.hbs");
  }


  public Void iniciarSesion(Request request, Response response) {
    modeloErrorLogin.put("error", false);

    try {
      Usuario usuario = RepositorioDeUsuarios.getINSTANCE().buscarPorUsuarioYContrasenia(
          request.queryParams("nombreUsuario"),
          request.queryParams("contrasenia"));

      request.session().attribute("user_id", usuario.getId_usuario());
      response.redirect("/establecimientos"); // TODO aca va a convenir leer el origen
      return null;
    } catch (Exception e) {
      modeloErrorLogin.replace("error", false, true);
      modeloErrorLogin.put("mantenerError", true);
        response.redirect("/login");
      return null;
    }
  }

  public ModelAndView mostrarRegistro(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modeloErrorLogin.remove("mantenerError");
    modeloErrorRestablecimiento.remove("mantenerError");
    boolean tieneErrores = modeloErrorRegistro.containsKey("errorYaExiste") ||
                           modeloErrorRegistro.containsKey("errorContrasenia") ||
                           modeloErrorRegistro.containsKey("errorInvalida");

    if(!modeloErrorRegistro.containsKey("mantenerError")) {
      tieneErrores = false;
    }

    modeloErrorRegistro.put("tieneErrores", tieneErrores);

    if(tieneErrores)
      return new ModelAndView(modeloErrorRegistro, "formulario-registro.html.hbs");
    else
      return new ModelAndView(modelo, "formulario-registro.html.hbs");
  }

  public Void registrar(Request request, Response response) {

    modeloErrorRegistro.put("errorYaExiste", false);
    modeloErrorRegistro.put("errorContrasenia", false);
    modeloErrorRegistro.put("errorInvalida", false);

      Usuario usuario = RepositorioDeUsuarios.getINSTANCE().buscarPorUsuario(
          request.queryParams("nombreUsuario"));

      if(usuario!= null){
        modeloErrorRegistro.replace("errorYaExiste", false, true);
      }

      if(!request.queryParams("contrasenia").equals(request.queryParams("otraContrasenia")))
        modeloErrorRegistro.replace("errorContrasenia", false, true);

      try{
        Usuario usuarioNuevo = new Usuario(request.queryParams("nombreUsuario"), request.queryParams("contrasenia"));
        RepositorioDeUsuarios.getINSTANCE().aniadirUsuario(usuarioNuevo);
        response.redirect("/login");
        return null;
      }
      catch (Exception e) {
        modeloErrorRegistro.replace("errorInvalida", false, true);
        modeloErrorRegistro.put("mantenerError", true);
        response.redirect("/registro");
        return null;
      }

  }

  public ModelAndView mostrarRestablecimiento(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modeloErrorLogin.remove("mantenerError");
    modeloErrorRegistro.remove("mantenerError");
    boolean tieneErroresRestablecimiento = modeloErrorRestablecimiento.containsKey("noExisteUsuario") ||
        modeloErrorRestablecimiento.containsKey("errorContrasenia") ||
        modeloErrorRestablecimiento.containsKey("errorInvalida");

    if(!modeloErrorRestablecimiento.containsKey("mantenerError")) {
      tieneErroresRestablecimiento = false;
    }

    modeloErrorRestablecimiento.put("tieneErrores", tieneErroresRestablecimiento);

    if(tieneErroresRestablecimiento)
      return new ModelAndView(modeloErrorRestablecimiento, "formulario-restablecimiento.html.hbs");
    else
      return new ModelAndView(modelo, "formulario-restablecimiento.html.hbs");
  }

  public Void restablecimiento(Request request, Response response) {
    Usuario usuario = RepositorioDeUsuarios.getINSTANCE().buscarPorUsuario(
        request.queryParams("nombreUsuario"));

    modeloErrorRestablecimiento.put("noExisteUsuario", false);
    modeloErrorRestablecimiento.put("errorContrasenia", false);
    modeloErrorRestablecimiento.put("errorInvalida", false);

    if(usuario == null){
      modeloErrorRestablecimiento.replace("noExisteUsuario", false, true);
    }

    if(!request.queryParams("contrasenia").equals(request.queryParams("otraContrasenia")))
      modeloErrorRestablecimiento.replace("errorContrasenia", false, true);

    try{
      String nuevaContrasenia = request.queryParams("contrasenia");
      RepositorioDeUsuarios.getINSTANCE().modificarUsuario(usuario, nuevaContrasenia);
      response.redirect("/login");
      return null;
    }
    catch (Exception e) {
      modeloErrorRestablecimiento.replace("errorInvalida", false, true);
      modeloErrorRestablecimiento.put("mantenerError", true);
      response.redirect("/restablecerContrasenia");
      return null;
    }

  }

  public ModelAndView mostrarPerfil(Request request, Response response){
    Map<String, Object> modelo = new HashMap<>();
    Integer id = request.session().attribute("user_id");
    Usuario usuario = RepositorioDeUsuarios.getINSTANCE().buscarPorId(id);
    List<Comunidad> comunidades = RepositorioComunidad.getInstancia().obtenerTodos();
    List<Comunidad> comunidadesAdmin = comunidades.stream().filter(comunidad-> comunidad.getAdministradores().contains(usuario)).toList();
    modelo.put("comunidadesAdmin", comunidadesAdmin);
    modelo.put("path","administracion");
    return new ModelAndView(modelo, "perfil.html.hbs");
  }

  public Void cerrarSesion(Request request, Response response){
    request.session().attribute("user_id", null);
    response.redirect("/login");
    return null;
  }

}
