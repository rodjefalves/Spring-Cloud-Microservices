package br.com.conductor.crudservice.feignclients;


import br.com.conductor.crudservice.entity.Produto;
import br.com.conductor.crudservice.rest.dto.ProdutoDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Component
@FeignClient(name = "pagamento", path = "/pagamento/api/produtos")
public interface ProdutoFeignClient {

   @GetMapping
    List<Produto> buscarTodosProdutosPag();

    @PostMapping
    Produto salvarProduto(@RequestBody Produto dto);

    @PutMapping
    Produto atualizarEstoqueProduto(@RequestBody @Valid ProdutoDTO dto);
}
