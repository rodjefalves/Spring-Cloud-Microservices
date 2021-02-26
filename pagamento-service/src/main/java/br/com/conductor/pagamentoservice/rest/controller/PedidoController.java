package br.com.conductor.pagamentoservice.rest.controller;

import br.com.conductor.pagamentoservice.rabbitmq.OrderQueueSender;
import br.com.conductor.pagamentoservice.repository.ProdutoRepository;
import br.com.conductor.pagamentoservice.rest.dto.*;
import br.com.conductor.pagamentoservice.entity.ItemPedido;
import br.com.conductor.pagamentoservice.entity.Pedido;
import br.com.conductor.pagamentoservice.enums.StatusPedido;
import br.com.conductor.pagamentoservice.service.PedidoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    @Autowired
    private OrderQueueSender orderQueueSender;

    @Autowired
    private ObjectMapper objectMapper;

    public PedidoController(PedidoService service, OrderQueueSender orderQueueSender) {
        this.service = service;
        this.orderQueueSender = orderQueueSender;
    }

    @PostMapping
    @ApiOperation(value = "Salvar pedido")
    @ResponseStatus(CREATED)
    public Integer save( @RequestBody @Valid PedidoDTO dto ){
        Pedido pedido = service.salvar(dto);

        int quant = pedido.getItens().size();

        for (int i = 0; i < quant; ++i) {

            Integer idp = dto.getItems().get(i).getProduto();
            Integer quantp = dto.getItems().get(i).getQuantidade();

            System.out.println("Id: " + idp + "; Quantidade: " + quantp);


            AtualizarEstoqueDTO AEDTO = new AtualizarEstoqueDTO();
            AEDTO.setId(idp);
            AEDTO.setQnt(quantp);

            EnviarMsg(AEDTO);

        }

        System.out.println(quant);

        return pedido.getId();
    }

    @ApiOperation(value = "Enviar mensagem via rabbit (para atualizar estoque)")
    @PostMapping("/rabbitsend")
    public void EnviarMsg(@RequestBody AtualizarEstoqueDTO atualizarEstoqueDTO){
        orderQueueSender.send(atualizarEstoqueDTO);
    }

    @ApiOperation(value = "Buscar pedido por id")
    @GetMapping("{id}")
    public InformacoesPedidoDTO getById( @PathVariable Integer id ){
        return service
                .obterPedidoCompleto(id)
                .map( p -> converter(p) )
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
    }

    @ApiOperation(value = "Atualizar status do pedido")
    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id ,
                             @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(
                item -> InformacaoItemPedidoDTO
                        .builder().descricaoProduto(item.getProduto().getNome())
                        .precoUnitario(item.getProduto().getValor())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }
}