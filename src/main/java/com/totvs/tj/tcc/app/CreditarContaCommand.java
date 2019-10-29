package com.totvs.tj.tcc.app;

import com.totvs.tj.tcc.domain.conta.Conta;
import com.totvs.tj.tcc.domain.conta.Movimento;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditarContaCommand {

    private Conta conta;
    
    private Movimento movimento;
}
