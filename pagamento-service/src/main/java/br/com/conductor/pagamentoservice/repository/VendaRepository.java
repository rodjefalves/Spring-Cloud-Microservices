package br.com.conductor.pagamentoservice.repository;

import br.com.conductor.pagamentoservice.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {
}
