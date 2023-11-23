package ar.edu.utn.frba.dds.domain.medioComunicacion;

import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import ar.edu.utn.frba.dds.exceptions.SeEnvioEmailException;


import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue( value = "Email")
public class MedioEmail extends MedioComunicacion {

    private MedioComunicacionAdapter medioComunicacionAdapter = new AdapterJackartaEmail();

    public MedioEmail(MedioComunicacionAdapter medioComunicacionAdapter) {
        this.medioComunicacionAdapter = medioComunicacionAdapter;
    }
    public MedioEmail(){}

    @Override
    public String notificarIncidente(Usuario usuario, Incidente incidente) {
        String emailUsuario = usuario.getEmail();
        try{
            String mensaje = "La comunidad: " +incidente.getComunidad()+ " reportó un nuevo incidente. Las observaciones son: " +incidente.getObservaciones();
            this.medioComunicacionAdapter.notificate(mensaje, emailUsuario);
            return "OK";
        } catch (SeEnvioEmailException error) {
            throw new SeEnvioEmailException("No se pudo enviar email a " + emailUsuario);
        }
    }

    @Override
    public String notificarServicioCercano(Usuario usuario, List<Servicio> servicios) {
        String emailUsuario = usuario.getEmail();
        try{
            List<String> serviciosIds = servicios.stream().map(servicio -> servicio.getId_servicio().toString()).collect(Collectors.toList());
            String mensaje = serviciosIds.stream().reduce(" ", (acumulador, serviciosId) -> acumulador + serviciosId + ", ");
            this.medioComunicacionAdapter.notificate("Los siguientes servicios son cercanosL: " + mensaje, "lurodriguezloza@frba.utn.edu.ar");
            return "OK";
        }catch (RuntimeException error) {
            throw new SeEnvioEmailException("No se pudo enviar email a " + emailUsuario);
        }
    }
}
