package uk.co.afe.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uk.co.afe.ValueConstants;
import uk.co.afe.model.client.Client;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Locale;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 15:54
 */
@Service
public class NotificationBean {

    @Value("${network.mail.backoffice}")
    private String backoffice;

    @Autowired
    private JavaMailSender sender;
    @Autowired
    private ClientBean clientBean;
    @Autowired
    private MessageSource messageSource;

    @PostConstruct
    private void init() {
        if (backoffice == null) {
            throw new RuntimeException("Application configuration exception. Back-office email should be defined");
        }
    }

    public void sendEmail(
            @Valid @NotNull Long clientId,
            @Valid @NotNull String subject,
            @Valid @NotNull String text) {
        Client client = clientBean.getClientById(clientId);
        sendEmail(client.getEmail(), subject, text);
    }

    public void sendEmail(
            @Valid @NotNull String email,
            @Valid @NotNull String subject,
            @Valid @NotNull String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        subject = messageSource.getMessage("subject.prefix", new String[]{subject}, ValueConstants.DEFAULT_LOCALE);
        message.setSubject(subject);
        message.setText(text);
        sender.send(message);
    }

    public void sendEmailToBackOffice(String subject, String text) {
        sendEmail(backoffice, subject, text);
    }

}
