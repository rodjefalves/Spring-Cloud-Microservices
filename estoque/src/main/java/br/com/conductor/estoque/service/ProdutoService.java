package br.com.conductor.estoque.service;

import br.com.conductor.estoque.entity.Produto;
import br.com.conductor.estoque.exception.ProdutoNaoEncontradoException;
import br.com.conductor.estoque.repository.ProdutoRepository;
import br.com.conductor.estoque.rest.dto.ProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private final ProdutoRepository produtoRepository;


    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public BigDecimal valorProduto(Integer id, Integer quantidade){

        BigDecimal valor = produtoRepository.getOne(id).getValor();

        BigDecimal produto = BigDecimal.valueOf(quantidade).multiply(valor);

        return produto;
    }

    public boolean existeProduto(Integer id){
        return produtoRepository.existsById(id);
    }


    public List<Produto> buscarTodosProdutos(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return produtoRepository.findAll(example);
    }


    public ProdutoDTO buscarUmProduto(Integer id){
        return produtoRepository.findById(id)
                .map(produto -> ProdutoDTO
                        .builder()
                        .id(produto.getId())
                        .estoque(produto.getEstoque())
                        .nome(produto.getNome())
                        .valor(produto.getValor())
                        .build())
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }


    public Produto salvarProduto(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setEstoque(dto.getEstoque());
        produto.setValor(dto.getValor());
        return produtoRepository.save(produto);
    }


    public Produto atualizarProduto(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setId(dto.getId());
        produto.setNome(dto.getNome());
        produto.setEstoque(dto.getEstoque());
        produto.setValor(dto.getValor());

        return produtoRepository.save(produto);
    }


    @Transactional
    public Produto atualizarEstoque(Integer produtoId, Integer quantidade) {
        Integer id = produtoId;

        Integer estoqueAtual = produtoRepository.getOne(id).getEstoque();

        Produto produto = new Produto();
        produto.setId(produtoRepository.getOne(id).getId());
        produto.setNome(produtoRepository.getOne(id).getNome());
        produto.setEstoque((estoqueAtual-quantidade));
        produto.setValor(produtoRepository.getOne(id).getValor());

        return produtoRepository.save(produto);
    }

    public void deletaProduto(Produto produto) {
        produtoRepository.delete(produto);
    }
}
