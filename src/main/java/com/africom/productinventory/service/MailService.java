package com.africom.productinventory.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void envoyerAlerteStock(String destinataire, String produit, int stock) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(destinataire);
            helper.setSubject("Alerte : Stock Faible");
            helper.setFrom(from);
            helper.setText(
                "<h3>Attention : Stock Faible</h3><p>Le stock du produit <strong>" + produit +
                    "</strong> est bas (<strong>" + stock + "</strong> unités).</p>",
                true
            );

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi d'email : " + e.getMessage(), e);
        }
    }
}
