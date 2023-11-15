package ar.edu.utn.frba.dds.domain.medioComunicacion;

//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class AdapterJackartaEmail {

  Properties properties = new Properties();
  String userName;
  String password;
  String port;

  String pathConfigFile = "src/main/java/ar/edu/utn/frba/dds/models/community/notification_channel/email/configEmail.properties";

  public AdapterJackartaEmail(){

    try (FileInputStream fileInputStream = new FileInputStream(pathConfigFile)) {
      properties.load(fileInputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.userName = properties.getProperty("user");
    this.password = properties.getProperty("password");
    this.port = properties.getProperty("port");

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


  public void notificate(String mensaje , String data) {
    MimeMessage message = new MimeMessage(session);
    try {
      message.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(data, true)});
      //asunto
      message.setSubject("TITULO DEL MAIL");
      //texto en el mail
      message.setText(mensaje);

      Transport.send(message);

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
