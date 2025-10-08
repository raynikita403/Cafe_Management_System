package in.nikita.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendContactMessage(String name, String fromEmail, String messageBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setReplyTo(fromEmail);// user's email
        message.setTo("acupofhappiness79@gmail.com"); // your email
        message.setSubject("Email from User of your shop " + name);
        message.setText("Name: " + name + "\nEmail: " + fromEmail + "\nMessage: " + messageBody);

        mailSender.send(message);
    }
}
