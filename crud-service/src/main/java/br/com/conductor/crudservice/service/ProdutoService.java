package br.com.conductor.crudservice.service;

import br.com.conductor.crudservice.entity.Produto;
import br.com.conductor.crudservice.exception.ProdutoNaoEncontradoException;
import br.com.conductor.crudservice.repository.ProdutoRepository;
import br.com.conductor.crudservice.rest.dto.ProdutoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
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


    /*public Produto atualizarProduto(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setId(dto.getId());
        produto.setNome(dto.getNome());
        produto.setEstoque(dto.getEstoque());
        produto.setValor(dto.getValor());

        return produtoRepository.save(produto);
    }*/

    public Produto atualizarEstoqueProduto(Integer produtoId, Integer quantidade) {
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
