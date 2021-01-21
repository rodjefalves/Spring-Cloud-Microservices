package br.com.conductor.pagamentoservice.repository;

import br.com.conductor.pagamentoservice.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
