package com.totvs.tj.tcc.app;

import com.totvs.tj.tcc.domain.conta.Empresa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbrirContaCommand {

    private Empresa empresa;
    
}
