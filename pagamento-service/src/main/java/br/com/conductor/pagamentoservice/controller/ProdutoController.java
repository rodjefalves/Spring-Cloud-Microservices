package br.com.conductor.pagamentoservice.controller;


import br.com.conductor.pagamentoservice.dto.ProdutoDTO;
import br.com.conductor.pagamentoservice.entity.Produto;
import br.com.conductor.pagamentoservice.service.ProdutoService;
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

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;

    }

    //Get produtos
    @ApiOperation(value = "Buscar todos os produtos")
    @GetMapping
    public List<Produto> buscarTodosProdutos(Produto filtro) {
       return produtoService.buscarTodosProdutos(filtro);
    }

    @ApiOperation(value = "Buscar um produto por id")
    @GetMapping("/{id}")
    public ProdutoDTO buscarUmProduto(@PathVariable(value = "id") Integer id){
        return produtoService.buscarUmProduto(id);
    }


    @PostMapping
    @ApiOperation(value = "Salvar um produto")
    public Produto salvarProduto(@RequestBody @Valid ProdutoDTO dto) {
        return produtoService.salvarProduto(dto);
    }


    @PutMapping
    @ApiOperation(value = "Atualizar um produto")
    public Produto atualizarProduto(@RequestBody @Valid ProdutoDTO dto) {
        return produtoService.atualizarProduto(dto);
    }


    @DeleteMapping
    @ApiOperation(value = "Deletar um produto")
    public void deletaProduto(@RequestBody Produto produto) {
        produtoService.deletaProduto(produto);
    }


    @ApiOperation(value = "Testando GET na Feign")
    @GetMapping("/teste")
    public String teste(){
        return "Feign no CRUD funcionou...";
    }

    @ApiOperation(value = "Testando GET id na Feign")
    @GetMapping("/teste/{id}")
    public String testeId(@PathVariable Integer id){
        String texto;
        if (id >= 1 && id <= 10) {
            texto = "O id está no intervalo entre 1 e 10 :D";
        } else {
            texto = "O id está fora do intervalo entre 1 e 10 :D";
        }
        return texto;
    }



}
