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

    public Emprestimo() {
        situacao = SituacaoEmprestimo.PENDENTE;
    }    
    
    public void reprovar(Emprestimo emprestimo) {
        this.situacao = situacao.nextState(emprestimo);
    }
    
    public void semLimiteDisponivel(Emprestimo emprestimo) {
        this.situacao = situacao.nextState(emprestimo);
    }

    public void aguardarAprovacao(Emprestimo emprestimo) {
        this.situacao = situacao.nextState(emprestimo);
    } 
    
    public void liberarEmprestimo(Emprestimo emprestimo) {
        this.situacao = situacao.nextState(emprestimo);
        this.empresa.alocarSaldoConta(this.valor);
    }
    
    public void quitarEmprestimo(Emprestimo emprestimo) {
        this.situacao = situacao.nextState(emprestimo);
        this.empresa.desalocarSaldoConta(this.valor);
    }
    
    public double getValorMaximoSemAprovacaoGerente() {
        return empresa.getContaLimite() * (porcentagemAprovacaoGerente/100);
    }
    
}
