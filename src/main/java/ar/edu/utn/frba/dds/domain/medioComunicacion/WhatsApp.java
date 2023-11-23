package ar.edu.utn.frba.dds.domain.medioComunicacion;

import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;
import ar.edu.utn.frba.dds.exceptions.SeEnvioEmailException;
import ar.edu.utn.frba.dds.exceptions.SeEnvioWhatsappException;;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue( value = "Whatsapp")
public class WhatsApp extends MedioComunicacion {

    private  MedioComunicacionAdapter medioComunicacionAdapter = new AdapterTwilioWhatsapp();

    public WhatsApp(MedioComunicacionAdapter medioComunicacionAdapter) {
        this.medioComunicacionAdapter = medioComunicacionAdapter;
    }
    public WhatsApp(){}

    @Override
    public String notificarIncidente(Usuario usuario, Incidente incidente) {
        String telefonoUsuario = usuario.getTelefono();
        try{
            String mensaje = "La comunidad: " +incidente.getComunidad()+ " reportó un nuevo incidente. Las observaciones son: " +incidente.getObservaciones();
            this.medioComunicacionAdapter.notificate(mensaje, telefonoUsuario);
            return "OK";
        }catch (SeEnvioWhatsappException error) {
            throw new SeEnvioWhatsappException("No se pudo enviar email a " + telefonoUsuario);
        }
    }

    @Override
    public String notificarServicioCercano(Usuario usuario, List<Servicio> servicios) {
        String telefonoUsuario = usuario.getTelefono();
        try{
            List<String> serviciosIds = servicios.stream().map(servicio -> servicio.getId_servicio().toString()).collect(Collectors.toList());
            String mensaje = serviciosIds.stream().reduce(" ", (acumulador, serviciosId) -> acumulador + serviciosId + ", ");
            this.medioComunicacionAdapter.notificate("HAY SERVICIOS CERCANOS " + mensaje, telefonoUsuario);
            return "OK";
        }catch (SeEnvioWhatsappException error) {
            throw new SeEnvioWhatsappException("No se pudo enviar email a " + telefonoUsuario);
        }
    }
}
