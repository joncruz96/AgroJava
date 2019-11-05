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
        this.situacao = situacao.nextState(this);
    }

    public void semLimiteDisponivel() {
        this.situacao = situacao.nextState(this);
    }

    public void aguardarAprovacao() {
        this.situacao = situacao.nextState(this);
    }
    
    public void aprovarEmprestimo() {
        this.situacao = situacao.nextState(this);    
        this.empresa.alocarSaldoConta(this.valor);
    }

    public void liberarEmprestimo() {
        this.situacao = situacao.nextState(this);
        this.empresa.alocarSaldoConta(this.valor);
    }

    public void quitarEmprestimo() {
        this.situacao = situacao.nextState(this);
        this.empresa.desalocarSaldoConta(this.valor);
    }

    public double getValorMaximoSemAprovacaoGerente() {
        return empresa.getContaLimite() * (getPorcentagemAprovacaoGerente() / 100);
    }

    public boolean getIsSemLimiteDisponivel() {
        return this.valor > empresa.getContaLimite();
    }

    public boolean getIsSupensa() {
        return empresa.isSupensa();
    }

    public boolean getIsAguardandoAprovacao() {
        return this.valor > getValorMaximoSemAprovacaoGerente();
    }

    

}
