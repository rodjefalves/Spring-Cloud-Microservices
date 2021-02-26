package br.com.conductor.pagamentoservice.feignclients;

import br.com.conductor.pagamentoservice.rest.dto.ProdutoDTO;
import br.com.conductor.pagamentoservice.entity.Produto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Component
@FeignClient(name = "crud", url = "localhost:8081", path = "/crud/api/produtos")
public interface ProdutoFeignClient {

    @GetMapping("/estoque")
    String teste2();

    @GetMapping
    List<Produto> buscarTodosProdutos();

    @PutMapping("/estoque")
    @ApiOperation(value = "Atualizar um produto")
    Produto atualizarEstoqueProduto(@RequestBody @Valid ProdutoDTO dto);
}
