package Final_Java.demo;

import Final_Java.demo.Data.Account;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Properties;

public class SendEmail {

    public LocalDateTime SendEmailAccess(Account account) {
        final int expirationSeconds = 60; // Thời gian hết hạn: 60 giây
        LocalDateTime expirationTime = LocalDateTime.now().plusSeconds(expirationSeconds);

        final String username = "dannguyenle282@gmail.com";
        final String accessToken = "texa wlex agsg cybo";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, accessToken);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(account.getEmail()));
            message.setSubject("Subject of the email");

            // Sử dụng thẻ HTML để chèn đường link trong nội dung email
            String emailContent = "This is the content of the email. Click <a href=\"http://localhost:8080/Admin/getEmail?id=" + account.getId() + "\">here</a> to access the link.";

            // Thiết lập nội dung của email dưới định dạng HTML
            message.setContent(emailContent, "text/html");

            // Gửi email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return expirationTime;
    }
}
