package com.totvs.tj.tcc.domain.conta;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "from")
public class MovimentoId {

    private String value;

    /**
     * Retorna o valor raiz do código.
     */
    @Override
    public String toString() {
        return value;
    }

    public static MovimentoId generate() {
        return MovimentoId.from(UUID.randomUUID().toString());
    }
    
}