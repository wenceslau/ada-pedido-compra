package com.ada.pedidocompra.dominio.servicos.pedido;

import com.ada.pedidocompra.infraestrutura.configuracao.exceptions.NegocioException;
import com.ada.pedidocompra.infraestrutura.repositorios.ProdutoRepository;
import com.ada.pedidocompra.infraestrutura.repositorios.entidades.ItemPedido;
import com.ada.pedidocompra.infraestrutura.repositorios.entidades.Pedido;
import com.ada.pedidocompra.infraestrutura.repositorios.entidades.Produto;
import com.ada.pedidocompra.infraestrutura.repositorios.entidades.enums.StatusPedidoEnum;
import jakarta.transaction.Transactional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Order(1)
@Service
public class PedidoValidarEstoqueService implements ProcessarPedidoService {

    private final ProdutoRepository produtoRepository;

    public PedidoValidarEstoqueService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    @Transactional
    public void processar(Pedido pedido) {

        pedido.getItens().forEach(itemPedido -> validarEstoque(pedido, itemPedido));

    }

    private void validarEstoque(Pedido pedido, ItemPedido itemPedido) {
        Optional<Produto> byId = produtoRepository.findById(itemPedido.getProduto().getId());

        if (byId.isEmpty()) {
          throw new NegocioException("Produto %s não encontrado".formatted(itemPedido.getProduto().getId()));
        }

        Produto produto = byId.get();
        if (produto.getEstoque() < itemPedido.getQuantidade()) {
            String mensagem = "Estoque Produto %s não disponível. Pedido cancelado!".formatted(produto.getDescricao());
            pedido.setStatus(StatusPedidoEnum.CANCELADO);
            pedido.setMensagemStatus(mensagem);
        }

    }

}
