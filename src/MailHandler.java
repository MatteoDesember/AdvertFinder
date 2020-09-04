import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import assets.MyFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.GregorianCalendar;
import java.util.Properties;

public class MailHandler {

    private static final String FILENAME = "data/MailSettings.json";
    private static Properties properties = new Properties();
    protected static Session session;
    private static Transport transport;
    protected String USERNAME = "email.adres@sample.com";
    protected String PASSWORD = "password";


    private void connect() throws MessagingException {
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        transport = session.getTransport("smtp");
        transport.connect(properties.getProperty("mail.smtp.host"),
                Integer.parseInt(properties.getProperty("mail.smtp.port")),
                properties.getProperty("mail.smtp.user"),
                properties.getProperty("mail.smtp.password"));
    }

    MailHandler() throws MessagingException {
        try {
            JsonObject jsonObject = new Gson().fromJson(new JsonReader(new FileReader(FILENAME)), JsonObject.class);
            if (jsonObject == null) {
                throw new NullPointerException("return value is null at method AAA");
            }
            this.USERNAME = jsonObject.get("USERNAME").getAsString();
            this.PASSWORD = jsonObject.get("PASSWORD").getAsString();
            connect();

        } catch (NullPointerException | FileNotFoundException e) {
            System.out.println("Set username and password in file: " + FILENAME);
            System.out.println(e.toString());
            String jsonObject = new GsonBuilder().setPrettyPrinting().create().toJson(this);
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(FILENAME)))) {
                bufferedWriter.write(jsonObject);
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void sendMail(Mail mail) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mail.getMailTo() + "," + USERNAME));
            message.setSubject(mail.getSubject());
            message.setText(mail.getMessage());

            transport.sendMessage(message, message.getAllRecipients());

        } catch (MessagingException e) {
            MyFile.write(
                    "data/unsend/" + AdvertFinder.dateFormatFileName.format(new GregorianCalendar().getTime()) + " " + mail.getMailTo() + ".json",
                    new GsonBuilder().setPrettyPrinting().create().toJson(this));
        }
    }
}
