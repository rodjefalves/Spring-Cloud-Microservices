package br.com.conductor.pagamentoservice.service.impl;

import br.com.conductor.pagamentoservice.rabbitmq.OrderQueueSender;
import br.com.conductor.pagamentoservice.rest.dto.ItemPedidoDTO;
import br.com.conductor.pagamentoservice.rest.dto.PedidoDTO;
import br.com.conductor.pagamentoservice.entity.Cliente;
import br.com.conductor.pagamentoservice.entity.ItemPedido;
import br.com.conductor.pagamentoservice.entity.Pedido;
import br.com.conductor.pagamentoservice.entity.Produto;
import br.com.conductor.pagamentoservice.enums.StatusPedido;
import br.com.conductor.pagamentoservice.exception.PedidoNaoEncontradoException;
import br.com.conductor.pagamentoservice.exception.RegraNegocioException;
import br.com.conductor.pagamentoservice.repository.ClienteRepository;
import br.com.conductor.pagamentoservice.repository.ItemPedidoRepository;
import br.com.conductor.pagamentoservice.repository.PedidoRepository;
import br.com.conductor.pagamentoservice.repository.ProdutoRepository;
import br.com.conductor.pagamentoservice.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clientesRepository;
    private final ProdutoRepository produtosRepository;
    private final ItemPedidoRepository itemsPedidoRepository;

    @Autowired
    private OrderQueueSender orderQueueSender;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto ) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());

        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        int quant = pedido.getItens().size();

        System.out.println("Quantidade: " + quant);

        Double valorTotal = new Double(0);


        for (int i = 0; i < quant; ++i) {

            Double bdQuant = pedido.getItens().get(i).getQuantidade().doubleValue();
            Double bdConvert = pedido.getItens().get(i).getProduto().getValor().doubleValue()*bdQuant;

            System.out.println(bdConvert);

            valorTotal += bdConvert;

        }

        System.out.println("Valor total: " + valorTotal);


        //BigDecimal result = valor.multiply(BigDecimal.valueOf(serializedObj2));

       //System.out.println("Resultado final: " + result);

        BigDecimal valorTotalBD = new BigDecimal(valorTotal);

        pedido.setTotal(valorTotalBD);

        System.out.println(valorTotalBD);


        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus( Integer id, StatusPedido statusPedido ) {
        repository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException() );
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }
}
