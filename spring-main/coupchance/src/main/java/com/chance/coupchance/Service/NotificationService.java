package com.chance.coupchance.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chance.coupchance.Entites.Validation;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {
    
    private final SendGrid sendGrid;
    private final String fromEmail;

    // Modifiez le constructeur pour ne pas utiliser @AllArgsConstructor
    public NotificationService(SendGrid sendGrid, 
                             @Value("${spring.sendgrid.from-email}") String fromEmail) {
        this.sendGrid = sendGrid;
        this.fromEmail = fromEmail;
    }

    public void sendNotification(Validation validation) {
        try {
            Email from = new Email(fromEmail);
            Email to = new Email(validation.getUser().getEmail());
            String subject = "Votre code d'activation";
            
            String message = String.format(
                "Bonjour %s,\n\nVotre code d'activation est : %s\n\nValable 20 minutes.",
                validation.getUser().getName(),
                validation.getCode()
            );
            
            Content content = new Content("text/plain", message);
            Mail mail = new Mail(from, subject, to, content);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            
            log.info("SendGrid Status Code: {}", response.getStatusCode());
            
            if (response.getStatusCode() >= 400) {
                log.error("Erreur SendGrid : {}", response.getBody());
                throw new RuntimeException("Échec de l'envoi de l'email");
            }
            
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi via SendGrid", e);
            throw new RuntimeException("Erreur d'envoi d'email");
        }
    }
}