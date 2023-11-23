package ar.edu.utn.frba.dds.domain.medioComunicacion;


import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class AdapterJackartaEmail implements MedioComunicacionAdapter{

  Properties properties = new Properties();
  String userName = "enviadordisenio@gmail.com";
  String password = "qyrckowpbzedxbsh";
  String port = "587";

//  String pathConfigFile = "src/main/java/ar/edu/utn/frba/dds/models/community/notification_channel/email/configEmail.properties";

  public AdapterJackartaEmail(){

//    try (FileInputStream fileInputStream = new FileInputStream(pathConfigFile)) {
//      properties.load(fileInputStream);
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//    this.userName = properties.getProperty("user");
//    this.password = properties.getProperty("password");
//    this.port = properties.getProperty("port");

    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", port);
  }

  Session session = Session.getDefaultInstance(properties, new Authenticator() {
    protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(userName, password);
    }
  });

  @Override
  public void notificate(String mensaje , String receptor) {
    MimeMessage message = new MimeMessage(session);
    try {
      message.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("lurodriguezloza@frba.utn.edu.ar", true)});
      message.setSubject("Conectados - Nuevo incidente cercano para usted");
      message.setText(mensaje);
      Transport.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
