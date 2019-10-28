package com.totvs.tj.tcc.domain.conta;

import static com.totvs.tj.tcc.domain.conta.Conta.Situacao.ABERTO;
import static com.totvs.tj.tcc.domain.conta.Conta.Situacao.SUSPENSO;

import java.util.Objects;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Conta {

    private ContaId id;
    
    private Empresa empresa;
    
    private Gerente gerente;

    private Situacao situacao;
    
    private final double maximoLimite = 15000;
      
    private final double limite;

    private double saldoAlocado = 0;
    
    private Conta(Builder builder) {
        this.id = builder.id;
        this.empresa  = Objects.requireNonNull(builder.empresa);
        this.gerente  = Objects.requireNonNull(builder.gerente);
        this.situacao = Objects.requireNonNull(builder.situacao);
        this.limite   = builder.limite;
    }
    

    public static Builder builder() {
        return new Builder();
    }
        
    public void suspender() {
        situacao = SUSPENSO;
    }

    public boolean isDisponivel() {
        return ABERTO.equals(situacao);
    }
    
    public boolean isLimiteParaOMovimento(double valorMovimento) {
        // TODO Auto-generated method stub
        if(valorMovimento > (this.limite - this.saldoAlocado)) {
            return false;
        }else {
            return true;
        }            
    }     
    
   
    public static class Builder {

        private ContaId id;
        
        private Empresa empresa;
        
        private Gerente gerente;

        private Situacao situacao;
        private final double maximoLimite = 15000;
        
        private double limite;

        public Builder id(ContaId id) {
            this.id = id;
            return this;
        }

        public Builder empresa(Empresa empresa) {
            this.empresa = empresa;
            return this;
        }

        public Builder gerente(Gerente gerente) {
            this.gerente = gerente;
            return this;
        }

        public Builder situacao(Situacao situacao) {
            this.situacao = situacao;
            return this;
        }
        
        public Conta build() {
            return new Conta(this);
        }

        public Conta buildAsNew() {
            this.situacao = Situacao.ABERTO;
            this.limite = getLimiteInicial();
            return this.build();
        }
        
        public double getLimiteInicial() {        
            if (isLimiteMaximo())
               return this.maximoLimite;
            
            return empresa.getNumerosDeFuncionarios() * empresa.getValorMercadoEmpresa();
        }
        
        public boolean isLimiteMaximo() {
           if (empresa.getNumerosDeFuncionarios() * empresa.getValorMercadoEmpresa() > this.maximoLimite)
               return true;
           return false;
        }
        
    }
    
    static enum Situacao {

        ABERTO, SUSPENSO;
        
    }

    
}
