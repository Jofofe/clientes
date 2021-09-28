package com.jofofe.clientes.service.specification;

import com.jofofe.clientes.domain.Cliente;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ClienteSpecifications {

    public static Specification<Cliente> hasNome(String nome) {
        return (cliente, cq, cb) -> {
            if (isEmpty(nome)) {
                return cb.conjunction();
            }
            return cb.equal(cliente.get("nome"), nome);
        };
    }

    public static Specification<Cliente> hasEmail(String email) {
        return (cliente, cq, cb) -> {
            if (isEmpty(email)) {
                return cb.conjunction();
            }
            return cb.equal(cliente.get("email"), email);
        };
    }

    public static Specification<Cliente> hasIdade(Integer idade) {
        return (cliente, cq, cb) -> {
            if (isNull(idade)) {
                return cb.conjunction();
            }
            return cb.equal(cliente.get("idade"), idade);
        };
    }

}
