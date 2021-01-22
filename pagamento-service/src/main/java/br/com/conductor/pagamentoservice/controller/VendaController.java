package br.com.conductor.pagamentoservice.controller;

import br.com.conductor.pagamentoservice.dto.ProdutoDTO;
import br.com.conductor.pagamentoservice.entity.Produto;
import br.com.conductor.pagamentoservice.entity.Venda;
import br.com.conductor.pagamentoservice.feignclients.ProdutoFeignClient;
import br.com.conductor.pagamentoservice.repository.VendaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendas")
@Api(value = "vendas")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoFeignClient produtoFeignClient;

    //Get vendas
    @GetMapping
    @ApiOperation(value = "Buscar todas as vendas")
    public List<Venda> buscarTodasVendas(){
        return this.vendaRepository.findAll();
    }

    //Get vendas por id
    @ApiOperation(value = "Buscar somente uma das vendas")
    @GetMapping("/{id}")
    public Optional<Venda> buscarUmaVenda(@PathVariable(value = "id") Integer id){
        return this.vendaRepository.findById(id);
    }

    //Save vendas
    @PostMapping
    @ApiOperation(value = "Salvar uma venda")
    public Venda salvarVenda(@RequestBody Venda venda){
        return vendaRepository.save(venda);
    }

    //Update vendas
    @PutMapping
    @ApiOperation(value = "Atualizar uma das vendas")
    public Venda atualizarVenda(@RequestBody Venda venda) {
        return vendaRepository.save(venda);
    }

    //Delete vendas
    @DeleteMapping
    @ApiOperation(value = "Deletar uma venda")
    public void deletaVenda(@RequestBody Venda venda) {
        vendaRepository.delete(venda);
    }


    //@GetMapping("/teste-feign")
    @ApiOperation(value = "Testando GET na Feign")
    public String testeFeign(){
        //return produtoFeignClient.teste();
        return "Testando... Apagar esse teste";
    }

    //@GetMapping("/teste-feign2")
    @ApiOperation(value = "Testando GET na Feign")
    public List<Produto> testeFeign2(Produto filtro){
        return produtoFeignClient.buscarTodosProdutos();
    }

    @PutMapping("/atualizar-estoque")
    @ApiOperation(value = "Atualizar estoque de um produto pela FEIGN")
    public Produto testeFeign3(@RequestBody @Valid ProdutoDTO dto){
        return produtoFeignClient.atualizarEstoqueProduto(dto);
    }
}
