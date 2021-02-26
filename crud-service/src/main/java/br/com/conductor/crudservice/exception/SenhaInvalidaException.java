package br.com.conductor.crudservice.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("Senha inválida");
    }
}