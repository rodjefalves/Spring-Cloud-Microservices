package br.com.conductor.pagamentoservice.repository;

import br.com.conductor.pagamentoservice.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}