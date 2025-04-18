package com.ada.pedidocompra.infraestrutura.controladores.request;

public record ProdutoFilterRequest(int page, int size, String filter) {
}
