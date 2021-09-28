package com.jofofe.clientes.exception.handler;

import com.jofofe.clientes.exception.EmailClienteJaCadastradoException;
import com.jofofe.clientes.exception.ClienteNaoCadastradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EmailClienteJaCadastradoException.class})
    public ResponseEntity clienteJaCadastrado(EmailClienteJaCadastradoException ex, WebRequest request) {
        log.debug("manipulação de ClienteJaCadastradoException...");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ClienteNaoCadastradoException.class})
    public ResponseEntity clienteNaoCadastrado(ClienteNaoCadastradoException ex, WebRequest request) {
        log.debug("manipulação de ClienteNaoCadastradoException...");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.debug("manipulação de MethodArgumentNotValidException...");
        List<String> errors = getErrors(ex);
        return new ResponseEntity<>(errors, status);
    }

    private List<String> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
    }

}
