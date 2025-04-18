package com.ada.pedidocompra.infraestrutura.servicos;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@Profile("prod")
public class SendEmailProdService implements EmailService {

    private final Logger log = Logger.getLogger(SendEmailDevService.class.getName());

    @Override
    public void sendEmail(String email, String assunto, String texto) {

        log.info("Email enviado com sucesso para: %s. Ambiente Dev".formatted(email));

    }
}
