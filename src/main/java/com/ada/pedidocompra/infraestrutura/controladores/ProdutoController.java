package com.ada.pedidocompra.infraestrutura.controladores;

import com.ada.pedidocompra.dominio.servicos.ProdutoService;
import com.ada.pedidocompra.infraestrutura.controladores.request.CreateProdutoRequest;
import com.ada.pedidocompra.infraestrutura.controladores.request.ProdutoFilterRequest;
import com.ada.pedidocompra.infraestrutura.controladores.response.IdResponse;
import com.ada.pedidocompra.infraestrutura.controladores.response.ProdutoResponsePage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/produtos")
@RestController
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<IdResponse> create(@RequestBody CreateProdutoRequest createProdutoRequest) {

        var idResponse = produtoService.criar(createProdutoRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(idResponse);
    }

//    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    @GetMapping
    public ResponseEntity<ProdutoResponsePage> listar(ProdutoFilterRequest produtoFilterRequest) {
        return ResponseEntity.ok(produtoService.listar(produtoFilterRequest));
    }

}
