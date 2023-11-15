package ar.edu.utn.frba.dds.domain.repositorios;

import ar.edu.utn.frba.dds.domain.servicio.EstadoIncidente;
import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Getter;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;

public class RepositorioIncidentes implements WithSimplePersistenceUnit {

  @Getter
  private static final RepositorioIncidentes INSTANCE = new RepositorioIncidentes();

  public Incidente buscarPorId(Integer id) {
    return entityManager().createQuery("from Incidente where id_incidente = :idInc", Incidente.class)
        .setParameter("idInc", id)
        .getResultList()
        .get(0);
  }

  public Void cerrarIncidente(Incidente incidente){
    EntityTransaction transaction = entityManager().getTransaction();
    try {
      transaction.begin();
      incidente.setEstado(EstadoIncidente.RESUELTO);
      incidente.setHorarioCierre(LocalDateTime.now());
      entityManager().flush();
    }catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
    }
    return null;
  }

}
