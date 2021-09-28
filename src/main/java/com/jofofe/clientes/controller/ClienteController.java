package com.jofofe.clientes.controller;

import com.jofofe.clientes.domain.Cliente;
import com.jofofe.clientes.dto.ClienteDTO;
import com.jofofe.clientes.dto.InformacoesClienteDTO;
import com.jofofe.clientes.mapper.ObjectMapper;
import com.jofofe.clientes.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/cliente")
@Api(value = "Cliente")
public class ClienteController {

    private final ClienteService clienteService;

    private final ObjectMapper mapper;

    @PostMapping
    @ApiOperation(value = "Incluir cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cliente salva com sucesso", response = Object.class),
            @ApiResponse(code = 404, message = "Alguma informação não foi encontrada"),
            @ApiResponse(code = 400, message = "Ocorreu algum erro negocial"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    public ResponseEntity incluirCliente(@Valid @RequestBody InformacoesClienteDTO informacoesClienteDTO) {
        clienteService.incluirCliente(mapper.convert(informacoesClienteDTO, Cliente.class));
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @ApiOperation(value = "Atualizar cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cliente atualizado com sucesso", response = Object.class),
            @ApiResponse(code = 404, message = "Alguma informação não foi encontrada"),
            @ApiResponse(code = 400, message = "Ocorreu algum erro negocial"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    public ResponseEntity atualizarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        clienteService.atualizarCliente(mapper.convert(clienteDTO, Cliente.class));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar-paginado")
    @ApiOperation(value = "Listar clientes paginados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Clientes paginados listados com sucesso", response = Object.class),
            @ApiResponse(code = 404, message = "Alguma informação não foi encontrada"),
            @ApiResponse(code = 400, message = "Ocorreu algum erro negocial"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    public @ResponseBody Page<Object> listarClientesPaginados(@PageableDefault(sort="email") Pageable paginacao,
                                                                         @RequestParam(name="email", required = false) String email,
                                                                         @RequestParam(name="nome", required = false) String nome,
                                                                         @RequestParam(name="idade", required = false) Integer idade) {
        return clienteService.listarClientesPaginados(paginacao, email, nome, idade);
    }

    @GetMapping("/listar")
    @ApiOperation(value = "Listar clientes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Clientes listados com sucesso", response = Object.class),
            @ApiResponse(code = 404, message = "Alguma informação não foi encontrada"),
            @ApiResponse(code = 400, message = "Ocorreu algum erro negocial"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    public @ResponseBody ResponseEntity listarClientes(@RequestParam(name="email", required = false) String email,
                                           @RequestParam(name="nome", required = false) String nome,
                                           @RequestParam(name="idade", required = false) Integer idade) {
        List<Object> clientes = clienteService.listarClientes(email, nome, idade).stream()
                .map(c -> mapper.convert(c, ClienteDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(clientes);

    }

}
