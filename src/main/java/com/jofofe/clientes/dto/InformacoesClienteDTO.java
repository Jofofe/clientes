package com.jofofe.clientes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InformacoesClienteDTO extends BaseDTO {

    @Email(message = "{email.valid}")
    private String email;

    @NotBlank(message = "{nome.not.blank}")
    private String nome;

    @NotNull(message = "{idade.not.null}")
    @Pattern(regexp = "^[0-9]+$", message = "{idade.not.valid}")
    private String idade;

}