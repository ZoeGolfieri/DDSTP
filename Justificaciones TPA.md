# Justificaciones TPA

* El usuario no contiene su rol debido a que puede tener distintos roles en las diferentes comunidades. Es la comunidad quién conoce a sus miembros y a sus administradores y la cual le otorgará distintos permisos. Los usuarios no conocen las comunidades a las que pertenecen ya que este tipo de relación, de muchos a muchos, dificulta la mantenibilidad del sistema.
* Las comunidades pueden solicitar dar de alta un servicio en una estación. Creemos que es solicitado ya que la estación es la única que conoce todos sus servicios. Además pensando en la cohesión y viendo el enunciado, las comunidades poseen otras responsabilidades, y no sería cohesivo seguir agregando algunas que no le pertenecen.
* Las estaciones conocen sus servicios por lo que son las que deben tener el alta/baja de los mismos.
* RegistroDeUsuario se encarga de crear los usuarios. Cuando un usuario quiere registrarse, el sistema válida si la contraseña ingresada cumple con los requerimientos de seguridad, de no ser así el sistema falla inmediatamente y le notifica al usuario la razón, otorgando robustez. También lo pensamos de esta forma ya que el usuario no sabe las validaciones que debe hacer el sistema para su registro, por lo que no puede tener un constructor propio.
* Los usuarios tras haber sido dados de alta pueden settear información personal si lo desean.
* Se puede ver reflejada la consistencia en la manera que resolvimos cómo diseñar los servicios y las líneas de transporte. En ambos casos existen diferentes tipos tanto de servicios como de líneas, por lo que lo primero que pensamos fue utilizar composición implementando una interfaz. Pero decidimos utilizar Enums debido a que brindan una mayor robustez al sistema y a la vez simplicidad, una desventaja es que nos quita extensibilidad.



