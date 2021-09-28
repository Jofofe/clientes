package com.jofofe.clientes.controller;

import com.google.gson.Gson;
import com.jofofe.clientes.ClientesApplication;
import com.jofofe.clientes.dto.ClienteDTO;
import com.jofofe.clientes.dto.InformacoesClienteDTO;
import com.jofofe.clientes.utill.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ClientesApplication.class})
public class ClienteControllerTest extends AbstractBaseControllerTest {

    @Test
    public void testarIncluirCliente() throws Exception {
        mockMvc.perform(post("/cliente")
                        .content(new Gson().toJson(criarClienteParaCadastro()).getBytes())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(Util.statusMatcher);
    }

    @Test
    public void testarAlterarCliente() throws Exception {
        mockMvc.perform(put("/cliente")
                        .content(new Gson().toJson(criarClienteParaAlteracao()).getBytes())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(Util.statusMatcher);
    }

    @Test
    public void testarBuscarCliente() throws Exception {
        mockMvc.perform(get("/cliente/" + 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(Util.statusMatcher);
    }

    @Test
    public void testarListarClientes() throws Exception {
        mockMvc.perform(get("/cliente/listar")
                        .param("email", "jojo@mail.com")
                        .param("nome", "Jãozin")
                        .param("idade", "29")
                )
                .andDo(print())
                .andExpect(Util.statusMatcher);
    }

    @Test
    public void testarListarPaginadoClientes() throws Exception {
        mockMvc.perform(get("/cliente/listar-paginado")
                        .param("email", "jojo@mail.com")
                        .param("nome", "Jãozin")
                        .param("idade", "29")
                )
                .andDo(print())
                .andExpect(Util.statusMatcher);
    }

    private InformacoesClienteDTO criarClienteParaCadastro() {
        return InformacoesClienteDTO.builder()
                .nome("dsfsd")
                .idade("29")
                .email("sajdaiok@skjdfslkfdj.com")
                .build();
    }

    private ClienteDTO criarClienteParaAlteracao() {
        return ClienteDTO.builder()
                .id(1)
                .nome("dsfsd")
                .idade("29")
                .email("sajdaiok@skjdfslkfdj.com")
                .build();
    }

}