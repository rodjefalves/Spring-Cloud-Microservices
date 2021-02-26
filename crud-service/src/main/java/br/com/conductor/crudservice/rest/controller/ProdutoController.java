package br.com.conductor.crudservice.rest.controller;

import br.com.conductor.crudservice.entity.Produto;
import br.com.conductor.crudservice.feignclients.ProdutoFeignClient;
import br.com.conductor.crudservice.rest.dto.ProdutoDTO;
import br.com.conductor.crudservice.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "produtos")
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoFeignClient produtoFeignClient;


    public ProdutoController(ProdutoService produtoService, ProdutoFeignClient produtoFeignClient) {
        this.produtoService = produtoService;
    }

    /*
     * Início das funções básicas do CRUD
     */

    //Get produtos
    @ApiOperation(value = "Buscar todos os produtos")
    @GetMapping
    public List<Produto> buscarTodosProdutos(Produto filtro) {
        return produtoService.buscarTodosProdutos(filtro);
    }

    //Get produto único
    @ApiOperation(value = "Buscar um produto por id")
    @GetMapping("/{id}")
    public ProdutoDTO buscarUmProduto(@PathVariable(value = "id") Integer id){
        return produtoService.buscarUmProduto(id);
    }


    //Create
    @PostMapping
    @ApiOperation(value = "Salvar um produto")
    public Produto salvarProduto(@RequestBody @Valid ProdutoDTO dto) {
        return produtoService.salvarProduto(dto);
    }

    //Update
    @PutMapping
    @ApiOperation(value = "Atualizar um produto")
    public Produto atualizarProduto(@RequestBody @Valid ProdutoDTO dto) {
        return produtoService.atualizarProduto(dto);
    }

    //Delete
    @DeleteMapping
    @ApiOperation(value = "Deletar um produto")
    public void deletaProduto(@RequestBody Produto produto) {
        produtoService.deletaProduto(produto);
    }

    /*
     * Fim das funções básicas do CRUD
     */

    /*//Atualizações com a Feign...

    @PutMapping("/estoque")
    //@ApiOperation(value = "Estoque por @RequestParam")
    public Produto atualizarEstoqueProduto(@RequestParam("id") Integer id, @RequestParam("quantidade") Integer quantidade) {
        return produtoService.atualizarEstoqueProduto(id, quantidade);
    }

    //Salvar produtos do pagamento
    @PutMapping("/atualizar-estoque")
    //@ApiOperation(value = "Atualizar estoque de um produto pela FEIGN")
    public Produto testeFeign3(@RequestBody @Valid ProdutoDTO dto){
        return produtoFeignClient.atualizarEstoqueProduto(dto);
    }

    //Get produtos do pagamento
    //@GetMapping("/teste5")
    public List<Produto> testeFeign2(Produto filtro){
        return produtoFeignClient.buscarTodosProdutosPag();
    }*/
}