package com.ada.pedidocompra.dominio.servicos;

import com.ada.pedidocompra.infraestrutura.controladores.request.CreateProdutoRequest;
import com.ada.pedidocompra.infraestrutura.controladores.request.ProdutoFilterRequest;
import com.ada.pedidocompra.infraestrutura.controladores.response.IdResponse;
import com.ada.pedidocompra.infraestrutura.controladores.response.ProdutoResponse;
import com.ada.pedidocompra.infraestrutura.controladores.response.ProdutoResponsePage;
import com.ada.pedidocompra.infraestrutura.repositorios.ProdutoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public IdResponse criar(CreateProdutoRequest createProdutoRequest) {
        var produto = createProdutoRequest.toProduto();
        produtoRepository.save(produto);
        return new IdResponse(produto.getId());
    }

    public ProdutoResponsePage listar(ProdutoFilterRequest produtoFilterRequest) {

        var filtro = produtoFilterRequest.filter();
        var pageRequest = PageRequest.of(produtoFilterRequest.page(), produtoFilterRequest.size());

        var produtosPage = produtoRepository.findProdutosLike(
                filtro,
                pageRequest
        );

        var produtosContent = produtosPage.getContent()
                .stream()
                .map(ProdutoResponse::from)
                .toList();

        return new ProdutoResponsePage(
                produtosPage.getTotalElements(),
                produtosPage.getTotalPages(),
                produtosContent
        );
    }

}
