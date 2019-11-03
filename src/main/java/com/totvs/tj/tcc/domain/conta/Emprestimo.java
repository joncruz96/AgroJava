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
    private final double porcentagemAprovacaoGerente = 25;
    
    
    public void reprovar() {
        this.situacao = SituacaoEmprestimo.REPROVADO;
    }
    
    public void semLimiteDisponivel() {
        this.situacao = SituacaoEmprestimo.SEM_LIMITE_DISPONIVEL;
    }
    
    public void aguardarAprovacao() {
        this.situacao = SituacaoEmprestimo.AGUARDANDO_APROVACAO;
    }  
    
    public void liberarEmprestimo() {
        this.situacao = SituacaoEmprestimo.LIBERADO;
        this.empresa.alocarSaldoConta(this.valor);
    }
    
    public void quitarEmprestimo() {
        this.situacao = SituacaoEmprestimo.QUITADO;
        this.empresa.desalocarSaldoConta(this.valor);
    }
    
    public double getValorMaximoSemAprovacaoGerente() {
        return empresa.getContaLimite() * (porcentagemAprovacaoGerente/100);
    }
    
    public void solicitarLiberacaoEmprestimo() {
        if(empresa.isSupensa()) {
            reprovar();
        }else if(valor > empresa.getContaLimiteAtual()) {
            semLimiteDisponivel();            
        }else if(valor > getValorMaximoSemAprovacaoGerente()) {
            aguardarAprovacao();
        } else {
            liberarEmprestimo();
        }
    }   

    public static enum SituacaoEmprestimo {
        AGUARDANDO_APROVACAO,
        LIBERADO,
        QUITADO,
        SEM_LIMITE_DISPONIVEL,
        REPROVADO,
        PENDENTE
    }

    

}
