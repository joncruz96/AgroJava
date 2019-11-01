package com.totvs.tj.tcc.app;

import com.totvs.tj.tcc.domain.conta.EmpresaId;
import com.totvs.tj.tcc.domain.conta.Responsavel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbrirContaCommand {

    private EmpresaId idEmpresa;
    private Responsavel responsavel;
    private String cnpj;
    private int numeroDeFuncionario;
    private double valorMercadoEmpresa;
    
 
}
