package ar.edu.utn.frba.dds.domain.repositorios;

import ar.edu.utn.frba.dds.domain.establecimiento.Establecimiento;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import javax.persistence.EntityTransaction;

public class RepositorioDeUsuarios implements WithSimplePersistenceUnit {
  @Getter
  private List<Usuario> usuariosDeLaPlataforma = new ArrayList<>();

  @Getter
  private static final RepositorioDeUsuarios INSTANCE = new RepositorioDeUsuarios();
  public void aniadirUsuario(Usuario usuario) {
    EntityTransaction transaction = entityManager().getTransaction();
    try {
      transaction.begin();
    usuariosDeLaPlataforma.add(usuario);
    entityManager().persist(usuario);
    entityManager().flush();
    transaction.commit();
  }catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
    }
  }

  public Void modificarUsuario(Usuario usuario, String contrasenia) {
    EntityTransaction transaction = entityManager().getTransaction();
    try {
      transaction.begin();
      usuario.setContrasenia(contrasenia);
      entityManager().flush();
    }catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
    }
    return null;
  }

  public Usuario findById(Integer id) {
    return (Usuario) usuariosDeLaPlataforma.stream().filter(usuario -> usuario.getId_usuario().equals(id));
  }

  public Usuario buscarPorId(Integer id) {
    return entityManager().createQuery("from Usuario where id_usuario = :idUser", Usuario.class)
        .setParameter("idUser", id)
        .getResultList()
        .get(0);
  }

  public Usuario buscarPorUsuarioYContrasenia(String nombreUsuario, String contrasenia) {
    return entityManager()
        .createQuery("from Usuario where nombreUsuario = :nombreUsuario and hashContrasenia = :hashContrasenia", Usuario.class)
        .setParameter("nombreUsuario", nombreUsuario)
        .setParameter("hashContrasenia", DigestUtils.sha256Hex(contrasenia))
        .getResultList()
        .get(0);
  }

  public Usuario buscarPorUsuario(String nombreUsuario) {
    try {
      return entityManager()
          .createQuery("from Usuario where nombreUsuario = :nombreUsuario", Usuario.class)
          .setParameter("nombreUsuario", nombreUsuario)
          .getResultList()
          .get(0);
    } catch (Exception e){
      return null;
    }
  }
}
