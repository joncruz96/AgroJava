package com.totvs.tj.tcc.domain.conta;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder()
@AllArgsConstructor(access = PRIVATE)
public class Emprestimo {

    private EmprestimoId id;
    private Empresa empresa;
    private SituacaoEmprestimo situacao;
    private double valor;

    public void verificarEmprestimoEmpresaSuspensa() {
        if (this.empresa.isSupensa())
            this.situacao = SituacaoEmprestimo.REPROVADO;
    }
    
    public void verificarLimite() {
        if (this.empresa.getLimite() > this.valor)
            this.situacao = SituacaoEmprestimo.SEM_LIMITE_DISPONIVEL;

     }

    public static enum SituacaoEmprestimo {
        AGUARDANDO_APROVACAO,
        LIBERADO,
        QUITADO,
        SEM_LIMITE_DISPONIVEL,
        REPROVADO
    }

    

}
