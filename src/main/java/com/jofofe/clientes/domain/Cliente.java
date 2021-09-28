package com.jofofe.clientes.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CLIENTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
public class Cliente extends BaseEntidade {

    @Id
    @Column(name = "IDCLIENTE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "IDADE", nullable = false)
    private String idade;

}

