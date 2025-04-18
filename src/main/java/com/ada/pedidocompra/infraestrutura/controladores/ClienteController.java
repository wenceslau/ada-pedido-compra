package com.ada.pedidocompra.infraestrutura.controladores;

import com.ada.pedidocompra.dominio.servicos.ClienteService;
import com.ada.pedidocompra.infraestrutura.controladores.request.CreateClienteRequest;
import com.ada.pedidocompra.infraestrutura.controladores.request.UpdateClienteRequest;
import com.ada.pedidocompra.infraestrutura.controladores.response.ClienteResponse;
import com.ada.pedidocompra.infraestrutura.controladores.response.IdResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping()
    public ResponseEntity<IdResponse> create(@RequestBody @Valid CreateClienteRequest createClienteRequest) {

        var clienteResponse = clienteService.create(createClienteRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clienteResponse);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteResponse> findById(@PathVariable(name = "clienteId") Long id) {

        var clienteResponse = clienteService.findById(id);

        return ResponseEntity
                .ok(clienteResponse);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> findAll() {

        var clienteList = clienteService.findAll();

        return ResponseEntity
                .ok(clienteList);
    }


    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateClienteRequest updateClienteRequest) {

        clienteService.update(id, updateClienteRequest);

        return ResponseEntity
                .noContent()
                .build();
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        clienteService.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

}
