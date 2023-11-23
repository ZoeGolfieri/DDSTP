package ar.edu.utn.frba.dds.domain.medioComunicacion;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AdapterTwilioWhatsapp implements MedioComunicacionAdapter{

  Properties properties = new Properties();

  private String ACCOUNT_SID = "ACe91fcebea7f0be5e2a95115a20d8c5f5";
  private String AUTH_TOKEN = "d9738494cfe27bbb3af52b0ddd7e3f43";
  private String sendNumber = "whatsapp:+5491124730290";

//  String pathConfigFile = "src/main/java/ar/edu/utn/frba/dds/models/community/notification_channel/whatsapp/configWhatsapp.properties";
// esto hay que ponerlo en un archivo configuracion

  public AdapterTwilioWhatsapp(){
//    try (FileInputStream fileInputStream = new FileInputStream(pathConfigFile)) {
//      properties.load(fileInputStream);
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//    this.ACCOUNT_SID = properties.getProperty("ACCOUNT_SID");
//    this.AUTH_TOKEN = properties.getProperty("AUTH_TOKEN");
//    this.sendNumber = properties.getProperty("sendNumber");
  }

  @Override
  public void notificate(String mensaje, String receptor) { //data es el numero del q hay que mandarle el msj
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
            new com.twilio.type.PhoneNumber("whatsapp:+5491124730290"), // el receptor
            new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
            mensaje).create();
  }
}
