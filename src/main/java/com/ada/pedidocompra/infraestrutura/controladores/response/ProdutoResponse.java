package com.ada.pedidocompra.infraestrutura.controladores.response;

import com.ada.pedidocompra.infraestrutura.repositorios.entidades.Produto;

import java.math.BigDecimal;

public record ProdutoResponse(Long id,
                              String descricao,
                              BigDecimal preco,
                              String foto) {

    public static ProdutoResponse from(Produto produto) {
        return new ProdutoResponse(produto.getId(),
                            produto.getDescricao(),
                            produto.getPreco(),
                            produto.getFoto());
    }

}
