package fr.cs.authentificationproject.services;


import fr.cs.authentificationproject.repositories.EmailSenderRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author slimane
 * @Project emailSpring
 */

@Service
public class EmailSendService implements EmailSenderRepository {

    private final JavaMailSender javaMailSender;

    public EmailSendService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String email){
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setText(email);
        simpleMessage.setFrom("testmailcoolspring@gmail.com");
        simpleMessage.setTo(to);
        simpleMessage.setSubject("Confirm your email");

        javaMailSender.send(simpleMessage);
 }

}
