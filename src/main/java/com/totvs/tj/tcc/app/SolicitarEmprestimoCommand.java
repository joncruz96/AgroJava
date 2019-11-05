package com.totvs.tj.tcc.app;

import com.totvs.tj.tcc.domain.conta.Empresa;
import com.totvs.tj.tcc.domain.conta.EmprestimoId;
import com.totvs.tj.tcc.domain.conta.SituacaoEmprestimo;

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
