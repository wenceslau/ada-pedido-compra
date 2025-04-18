package com.ada.pedidocompra.infraestrutura.servicos;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@Profile("dev")                                                                                                         //
public class SendEmailDevService implements EmailService {

    private final JavaMailSender javaMailSender;
    private final Logger log = Logger.getLogger(SendEmailDevService.class.getName());

    public SendEmailDevService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async                                                                                                              // Não bloque a thread principal, envia o email em uma thread separada
    public void sendEmail(String email, String assunto, String texto) {


        var message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(assunto);
        message.setText(texto);
        javaMailSender.send(message);

        log.info("Email enviado com sucesso para: %s. Ambiente Dev".formatted(email));


    }

}
