package com.ada.pedidocompra.infraestrutura.controladores.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreatePedidoRequest(@NotNull List<ItemPedidoRequest> itens) {
}
