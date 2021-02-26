package br.com.conductor.pagamentoservice.service;

import br.com.conductor.pagamentoservice.rest.dto.PedidoDTO;
import br.com.conductor.pagamentoservice.entity.Pedido;
import br.com.conductor.pagamentoservice.enums.StatusPedido;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto );
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}