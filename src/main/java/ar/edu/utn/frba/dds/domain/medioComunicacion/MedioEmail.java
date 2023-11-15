package ar.edu.utn.frba.dds.domain.medioComunicacion;

import ar.edu.utn.frba.dds.domain.localizacion.Localizacion;
import ar.edu.utn.frba.dds.domain.servicio.Incidente;
import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import ar.edu.utn.frba.dds.domain.usuario.Usuario;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import ar.edu.utn.frba.dds.exceptions.SeEnvioEmailException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import java.util.Arrays;
import java.util.Properties;

@DiscriminatorValue( value = "Email")
public class MedioEmail extends MedioComunicacion {

    private final String username = "emailRemitente";
    private final String accessToken = "tokenAcceso";
    private final String host = "smtp.gmail.com";
    private final String user = "usuario";

    Properties props = new Properties();
    private void enviarNotificacion(String emailUsuario, String mensaje) {

        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.mail.sender","emisor@gmail.com");
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, accessToken);
            }
        });



        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailUsuario));
            message.setSubject("Notificación de incidente");
            message.setText(mensaje);

            Transport transport = session.getTransport();
            transport.connect(host, user, accessToken);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (MessagingException e) {
            throw new SeEnvioEmailException("Error al enviar el correo electrónico: " + e.getMessage());
        }


    }

    //REVISAR RETURN DE LOCALIZACIÓN
    @Override
    public Localizacion notificarIncidente(Usuario usuario, Incidente incidente) {
        String mailUsuario = usuario.getEmail();
        String mensaje = "La comunidad: "+incidente.getComunidad()+" reportó un nuevo incidente. Las observaciones son: "+incidente.getObservaciones();
        this.enviarNotificacion(mailUsuario,mensaje);
        return usuario.getLocalizacion_actual();    //NO TENGO IDEA QUE LOCALIZACION HABIA QUE DEVOLVER NI POR QUÉ, LA AGERGUÉ PORQUE ASI ESTABA, NO SÉ SI ES NECESARIO
    }

    @Override
    public void notificarServicioCercano(Usuario usuario, List<Servicio> servicios) {
        String mailUsuario = usuario.getEmail();
        servicios.stream().map(
            servicio -> {
                this.enviarNotificacion(mailUsuario, "estas cerca del servicio: " + servicio.getTipo());
                return null;}
            );
    }
}
