package ar.edu.utn.frba.dds.domain.medioComunicacion;

import ar.edu.utn.frba.dds.domain.localizacion.Localizacion;
import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "medioComunicación")
public abstract class MedioComunicacion { // esto deberia ser una interfaz?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medioComunicacion")
    private Integer id_medioComunicacion;

    public String notificarIncidente(Usuario usuario, Incidente incidente){return "OK";}

    public String notificarServicioCercano(Usuario usuario, List<Servicio> servicios) {return "OK";}
}


