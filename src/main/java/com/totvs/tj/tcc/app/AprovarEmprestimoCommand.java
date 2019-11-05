package com.totvs.tj.tcc.app;

import com.totvs.tj.tcc.domain.conta.Emprestimo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AprovarEmprestimoCommand {

    private Emprestimo emprestimo;

}
