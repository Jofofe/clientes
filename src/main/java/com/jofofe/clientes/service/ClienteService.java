package com.jofofe.clientes.service;

import com.jofofe.clientes.domain.Cliente;
import com.jofofe.clientes.dto.ClienteDTO;
import com.jofofe.clientes.dto.InformacoesClienteDTO;
import com.jofofe.clientes.exception.ClienteNaoCadastradoException;
import com.jofofe.clientes.exception.EmailClienteJaCadastradoException;
import com.jofofe.clientes.mapper.ObjectMapper;
import com.jofofe.clientes.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.jofofe.clientes.service.specification.ClienteSpecifications.*;


@Slf4j
@Service
public class ClienteService extends AbstractService<Cliente, Integer, ClienteRepository> {

    private final ObjectMapper mapper;

    ClienteService(ClienteRepository repository, ObjectMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    public void incluirCliente(Cliente cliente) {
        if(!repository.findByEmail(cliente.getEmail()).isPresent()) {
            repository.save(cliente);
        } else {
            throw new EmailClienteJaCadastradoException();
        }
    }

    public void atualizarCliente(Cliente cliente) {
        if(repository.findById(cliente.getId()).isPresent()) {
            repository.save(cliente);
        } else {
            throw new ClienteNaoCadastradoException();
        }
    }

    public Cliente buscarCliente(Integer idCliente) {
        return repository.findById(idCliente).orElseThrow(ClienteNaoCadastradoException::new);
    }

    public Page<Object> listarClientesPaginados(Pageable paginacao, String email, String nome, Integer idade) {
        List<Object> clientes = listarClientes(email, nome, idade).stream()
                .map(c -> mapper.convert(c, ClienteDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(clientes, paginacao, clientes.size());
    }

    public List<Cliente> listarClientes(String email, String nome, Integer idade) {
        return repository.findAll(Specification
                .where(hasEmail(email)).and(hasNome(nome)).and(hasIdade(idade)));
    }

}
