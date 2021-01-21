package br.com.conductor.autenticacaoservice.rest.controller;

public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException() {
        super("Senha inválida.");
    }
}