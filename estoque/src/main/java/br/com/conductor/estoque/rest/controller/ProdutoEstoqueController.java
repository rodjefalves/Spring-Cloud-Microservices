package br.com.conductor.estoque.rest.controller;

import br.com.conductor.estoque.rest.dto.AtualizaEstoqueDTO;
import br.com.conductor.estoque.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "produtos")
@RequestMapping("/api/produtos")
public class ProdutoEstoqueController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoEstoqueController(ProdutoService produtoService) {
        this.produtoService = produtoService;

    }


    @ApiOperation(value = "Atualizar estoque via RabbitMQ")
    @PutMapping("/estoque")
    public void testeAtualizarEstoque (@RequestBody AtualizaEstoqueDTO dto){
        produtoService.atualizarEstoque(dto.getId(), dto.getQnt());
        System.out.println("Funcionou!!!");
    }


}
