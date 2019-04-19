import javax.mail.Session
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import java.util.Properties
import javax.mail.Message
import javax.mail.internet.MimeMessage
import javax.mail.internet.InternetAddress
import javax.mail.Transport


public class SMTPAuthenticator extends Authenticator
{
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication("", "");
    }
}

node {
  def host = "smtp-mail.outlook.com"
  def port = 587
  String user = ""
  String passw = ""

  echo 'Configuring SMTP...'
  def props = new Properties()
  props.put("mail.smtp.auth", "true")
  props.put("mail.smtp.starttls.enable", "true")
  props.put("mail.smtp.host", host)
  props.put("mail.smtp.port", port)

  def auth = new SMTPAuthenticator()
  def session = Session.getInstance(props, auth)

  echo 'Building Email...'
  def msg = new MimeMessage(session)
  msg.setText("Lo has logrado. Eres un genio!")
  msg.setSubject("Lo hemos logrado.")
  msg.setFrom(new InternetAddress(user))
  msg.setRecipient(Message.RecipientType.TO, new InternetAddress(""))


  echo 'Sending Email...'
  def transport = session.getTransport("smtp")
  transport.connect(host, port, user, passw)
  transport.sendMessage(msg, msg.getAllRecipients())
  transport.close()
}
