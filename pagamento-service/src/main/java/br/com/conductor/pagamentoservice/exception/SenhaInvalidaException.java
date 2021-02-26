package br.com.conductor.pagamentoservice.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("Senha inválida");
    }
}