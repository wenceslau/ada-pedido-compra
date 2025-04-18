package com.ada.pedidocompra.infraestrutura.repositorios;

import com.ada.pedidocompra.infraestrutura.repositorios.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
