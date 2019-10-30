package com.totvs.tj.tcc.domain.conta;

import static lombok.AccessLevel.PRIVATE;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder()
@AllArgsConstructor(access = PRIVATE)
public class Empresa {

    private EmpresaId id;

    private Responsavel responsavel;

    private double valorMercadoEmpresa;

    private String cnpj;

    private Conta conta;

    private int numerosDeFuncionarios;

    private Situacao situacao;
    
    private Empresa(Builder builder) {
        this.id = builder.id;
        this.responsavel = Objects.requireNonNull(builder.responsavel);
        this.valorMercadoEmpresa = builder.valorMercadoEmpresa;
        this.cnpj = builder.cnpj;
        this.conta = Objects.requireNonNull(builder.conta);
        this.numerosDeFuncionarios = builder.numerosDeFuncionarios;
        this.situacao = builder.situacao;
    }
    
    public static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private EmpresaId id;

        private Responsavel responsavel;

        private double valorMercadoEmpresa;

        private String cnpj;

        private Conta conta;

        private int numerosDeFuncionarios;

        private Situacao situacao;
        private final double maximoLimite = 15000;
        

        public Builder id(EmpresaId id) {
            this.id = id;
            return this;
        }

        public Builder responsavel(Responsavel responsavel) {
            this.responsavel = responsavel;
            return this;
        }

        public Builder valorMercadoEmpresa(double valorMercadoEmpresa) {
            this.valorMercadoEmpresa = valorMercadoEmpresa;
            return this;
        }

        public Builder cnpj(String cnpj) {
            this.cnpj = cnpj;
            return this;
        }

        public Builder Conta(Conta conta) {
            this.conta = conta;
            return this;
        }

        public Builder numerosDeFuncionarios(int numerosDeFuncionarios) {
            this.numerosDeFuncionarios = numerosDeFuncionarios;
            return this;
        }

        public Empresa build() {
            return new Empresa(this);
        }

        public Empresa buildAsNew() {
            this.situacao = Situacao.ABERTO;
            this.conta = Conta.builder()
                    .saldoAlocado(0)
                    .limite(getLimiteInicial())
                    .build();
            return this.build();
        }

        public double getLimiteInicial() {
            if (this.numerosDeFuncionarios * this.valorMercadoEmpresa > this.maximoLimite)
                return this.maximoLimite;

            return this.numerosDeFuncionarios * this.valorMercadoEmpresa;
        }
        
        public double getContaLimite() {
            return this.conta.getLimite();
        }
        
        public double getContaSaldo() {
            return this.conta.getSaldoAlocado();
        }

    }

    static enum Situacao {
        ABERTO,
        SUSPENSO;
    }

}
