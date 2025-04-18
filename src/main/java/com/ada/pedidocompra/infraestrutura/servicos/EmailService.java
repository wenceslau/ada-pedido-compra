package com.ada.pedidocompra.infraestrutura.servicos;

public interface EmailService {

    void sendEmail(String email, String assunto, String texto);

}
