package backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.security.SecureRandom;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class EmailService {
    @Value("${frontend.url}")
    private String frontendUrl;
    private final String username;
    private final String password;

    @Autowired
    public EmailService(@Value("${email.username}") String username, @Value("${email.password}") String password) {
        this.username = username;
        this.password = password;
    }

    public void sendVerificationEmail(String recipientEmail, String verifyCode) {

        // Cấu hình thông tin máy chủ email
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Tạo phiên làm việc
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Xác minh tài khoản của bạn");

            // Đọc nội dung tệp HTML
            String emailTemplate = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/verification_email_template.html")));
            String verificationLink = frontendUrl + "/api/account/verify/";

            // Thay thế các biến trong tệp HTML
            String emailContent = emailTemplate
                    .replace("${verificationLink}", verificationLink)
                    .replace("${verifyCode}", verifyCode);

            message.setContent(emailContent, "text/html; charset=utf-8");

            // Gửi email
            Transport.send(message);
            System.out.println("Email đã được gửi thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage());
        }
    }

}