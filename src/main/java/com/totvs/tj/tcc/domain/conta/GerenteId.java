package com.totvs.tj.tcc.domain.conta;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "from")
public class GerenteId {

    private String value;

    /**
     * Retorna o valor raiz do códito.
     */
    @Override
    public String toString() {
        return value;
    }

    public static GerenteId generate() {
        return GerenteId.from(UUID.randomUUID().toString());
    }
}
