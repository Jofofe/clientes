package com.jofofe.clientes.exception;

public class EmailClienteJaCadastradoException extends RuntimeException {

    public EmailClienteJaCadastradoException() {
        super("Email do cliente já está cadastrado");
    }
}
