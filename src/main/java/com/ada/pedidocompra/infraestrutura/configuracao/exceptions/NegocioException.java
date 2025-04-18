package com.ada.pedidocompra.infraestrutura.configuracao.exceptions;

public class NegocioException extends RuntimeException {

    public NegocioException(String msg) {
        super(msg);
    }

}
