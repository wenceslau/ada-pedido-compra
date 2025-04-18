package com.ada.pedidocompra.dominio.servicos.pedido;

import com.ada.pedidocompra.infraestrutura.repositorios.entidades.Pedido;

public interface ProcessarPedidoService {

    void processar(Pedido pedido);

}

/*

Characteristics of the Strategy Pattern in this context:

1 - Encapsulation of Algorithms: The logic for processing a Pedido is encapsulated in different implementations of the
    ProcessarPedidoService interface.

2 - Open/Closed Principle: New processing strategies can be added without modifying the PedidoService class.

3 - Dependency Injection: The list of strategies (processarPedidoServiceList) is injected into the PedidoService class,
    promoting flexibility and testability.

This design is a good example of the Strategy Pattern, as it delegates specific behaviors (processing a Pedido) to
interchangeable strategy implementations.

 */
