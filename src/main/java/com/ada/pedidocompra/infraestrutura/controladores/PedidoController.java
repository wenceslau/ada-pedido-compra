package com.ada.pedidocompra.infraestrutura.controladores;

import com.ada.pedidocompra.dominio.servicos.PedidoService;
import com.ada.pedidocompra.infraestrutura.controladores.request.CreatePedidoRequest;
import com.ada.pedidocompra.infraestrutura.controladores.response.IdResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    //@PreAuthorize("hasRole('CLIENTE')")
    @PostMapping
    public ResponseEntity<IdResponse> realizarPedido(@RequestBody @Valid CreatePedidoRequest createPedidoRequest) {

        var pedidoId = pedidoService.create(createPedidoRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED).
                body(pedidoId);
    }

}
