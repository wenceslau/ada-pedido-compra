package com.ada.pedidocompra.infraestrutura.repositorios;

import com.ada.pedidocompra.infraestrutura.repositorios.entidades.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("select p from Produto p where upper(p.descricao) like upper(concat('%', :filtro, '%'))")
    Page<Produto> findProdutosLike(String filtro, Pageable pageable);

    @Query(value = "select p.descricao, p.preco from produtos p where p.descricao like (% ?1 %)", nativeQuery = true)
    Page<Produto> findProdutosLike2(String filtro, Pageable pageable);

    Page<Produto> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);

}
