package com.jofofe.clientes.exception;

public class ClienteNaoCadastradoException extends RuntimeException {

    public ClienteNaoCadastradoException() {
        super("Cliente não cadastrado");
    }
}
