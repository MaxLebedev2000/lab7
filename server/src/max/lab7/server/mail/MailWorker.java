package max.lab7.server.mail;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailWorker {

    private static final String login = "max_lebedev_lab7@mail.ru";
    private static final String password = "PRIMITELABYPOJALUYSTA";

    private Session session;

    public MailWorker() {
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.connectiontimeout", "1000");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        this.session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        });
    }

    public boolean send(String theme, String message, String toEmail) throws MessagingException{
        Message msg = new MimeMessage(session);


            msg.setFrom(new InternetAddress(login));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject(theme);
            msg.setText(message);

            Transport.send(msg);

            return true;


    }

}
