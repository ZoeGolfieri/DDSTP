package ar.edu.utn.frba.dds.domain;

import ar.edu.utn.frba.dds.domain.repositorios.RepositorioDeUsuarios;

public class Main {

    // dos tipos de notificaciones:
    // 1) una es en X tiempo para mostrar los incidentes (cronJob)
    // 2) Cuando esta cerca de un incidente ( en establecimiento o en usuario )
    // 3) Cuando una entidad agrega un incidente a uno de sus servicios ( en establecimiento )

    public static void notificarIncidentes() { // ej: se corre cada 1 hora
      RepositorioDeUsuarios repousuario = RepositorioDeUsuarios.getINSTANCE();
      repousuario.getUsuariosDeLaPlataforma()
          .forEach(usuario->usuario.notificarIncidentes());
    }
}


