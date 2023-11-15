package ar.edu.utn.frba.dds.domain.medioComunicacion;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AdapterTwilioWhatsapp {

  Properties properties = new Properties();

  private String ACCOUNT_SID;
  private String AUTH_TOKEN;
  private String sendNumber;

  String pathConfigFile = "src/main/java/ar/edu/utn/frba/dds/models/community/notification_channel/whatsapp/configWhatsapp.properties";


  public AdapterTwilioWhatsapp(){
    try (FileInputStream fileInputStream = new FileInputStream(pathConfigFile)) {
      properties.load(fileInputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.ACCOUNT_SID = properties.getProperty("ACCOUNT_SID");
    this.AUTH_TOKEN = properties.getProperty("AUTH_TOKEN");
    this.sendNumber = properties.getProperty("sendNumber");
  }

  public void notificate(String mensaje, String data) { //data es el numero del q hay que mandarle el msj

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
            new com.twilio.type.PhoneNumber("whatsapp:+"+data),
            new com.twilio.type.PhoneNumber(sendNumber),
            mensaje)
        .create();


  }
}
