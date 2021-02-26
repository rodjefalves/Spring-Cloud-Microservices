package br.com.conductor.estoque.rabbitmq;

import br.com.conductor.estoque.rest.dto.AtualizaEstoqueDTO;
import br.com.conductor.estoque.service.ProdutoService;
import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderConsumer {

    @Autowired
    public ProdutoService produtoService;

    @Autowired
    public ObjectMapper objectMapper;

    @RabbitListener(queues = {"${queue.order.name}"})
    public void receive(String message) throws JsonProcessingException {
        AtualizaEstoqueDTO atualizaEstoqueDTO = objectMapper.readValue(message, AtualizaEstoqueDTO.class);
        produtoService.atualizarEstoque(atualizaEstoqueDTO.getId(), atualizaEstoqueDTO.getQnt());
    }

}

