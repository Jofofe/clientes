package com.jofofe.clientes.service;

import com.jofofe.clientes.ClientesApplication;
import com.jofofe.clientes.controller.AbstractBaseControllerTest;
import com.jofofe.clientes.domain.Cliente;
import com.jofofe.clientes.exception.ClienteNaoCadastradoException;
import com.jofofe.clientes.exception.EmailClienteJaCadastradoException;
import com.jofofe.clientes.repository.ClienteRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ClientesApplication.class})
public class ClienteServiceTest extends AbstractBaseControllerTest {

    @InjectMocks
    ClienteService clienteService;

    @Mock
    private ClienteRepository repository;

    @Test(expected = EmailClienteJaCadastradoException.class)
    public void testarIncluirClienteEmailDuplicado() {
        Mockito.when(repository.findByEmail(anyString())).thenReturn(Optional.of(criarCliente()));
        clienteService.incluirCliente(criarCliente());
        verify(repository, never()).save(any(Cliente.class));
    }

    @Test
    public void testarIncluirCliente() {
        Mockito.when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        clienteService.incluirCliente(criarCliente());
        verify(repository, atLeastOnce()).save(any(Cliente.class));
    }

    @Test(expected = ClienteNaoCadastradoException.class)
    public void testarAlterarClienteNaoExistente() {
        Mockito.when(repository.findById(anyInt())).thenReturn(Optional.empty());
        clienteService.atualizarCliente(criarCliente());
        verify(repository, never()).save(any(Cliente.class));
    }

    @Test
    public void testarAlterarCliente() {
        Mockito.when(repository.findById(anyInt())).thenReturn(Optional.of(criarCliente()));
        clienteService.atualizarCliente(criarCliente());
        verify(repository, atLeastOnce()).save(any(Cliente.class));
    }

    @Test(expected = ClienteNaoCadastradoException.class)
    public void testarBuscarClienteNaoExistente() {
        Mockito.when(repository.findById(anyInt())).thenReturn(Optional.empty());
        clienteService.buscarCliente(1);
        verify(repository, never()).save(any(Cliente.class));
    }

    @Test
    public void testarBuscarCliente() {
        Mockito.when(repository.findById(anyInt())).thenReturn(Optional.of(criarCliente()));
        Cliente cliente = clienteService.buscarCliente(1);
        Assert.assertNotNull(cliente);
    }

    @Test
    public void testarListarClientes() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(criarCliente()));
        clienteService.listarClientes("dsdkf", "trrt", 23);
        verify(repository, atLeastOnce()).findAll(any());
    }

    @Test
    public void testarListarClientesPaginados() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(criarCliente()));
        clienteService.listarClientesPaginados(getPaginacao(),"dsdkf", "trrt", 23);
        verify(repository, atLeastOnce()).findAll(any());
    }

    private Cliente criarCliente() {
        return Cliente.builder()
                .id(1)
                .nome("dsfsd")
                .idade("29")
                .email("sajdaiok@skjdfslkfdj.com")
                .build();
    }

    private Pageable getPaginacao() {
        return new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
    }
}