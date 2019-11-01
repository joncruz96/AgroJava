package com.totvs.tj.tcc.app;

import com.totvs.tj.tcc.domain.conta.Empresa;
import com.totvs.tj.tcc.domain.conta.Emprestimo.SituacaoEmprestimo;
import com.totvs.tj.tcc.domain.conta.EmprestimoId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SolicitarEmprestimoCommand {

    private Empresa empresa;
    private EmprestimoId idEmprestimo;
    private double valor;
    private SituacaoEmprestimo situacao;
    
}
