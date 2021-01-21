package br.com.conductor.pagamentoservice.controller;


public class ProdutoNaoEncontradoException extends RuntimeException{

    public ProdutoNaoEncontradoException(Integer id) {
        super("O produto de id " + id + " não foi encontrado.");
    }
}