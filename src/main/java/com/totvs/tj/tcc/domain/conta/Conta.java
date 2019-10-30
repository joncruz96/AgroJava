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
public class Conta {
    
    private final double maximoLimite = 15000;

    private final double limite;
    
    private double saldoAlocado;    

}

/*public class Conta {

    private ContaId id;

    private Empresa empresa;

    private Gerente gerente;

    private Situacao situacao;

    private final double maximoLimite = 15000;

    private final double limite;
    
    private double saldoAlocado = 0;

    private List<Movimento> movimentos;

    private Conta(Builder builder) {
        this.id = builder.id;
        this.empresa = Objects.requireNonNull(builder.empresa);
        this.gerente = Objects.requireNonNull(builder.gerente);
        this.situacao = Objects.requireNonNull(builder.situacao);
        this.limite = builder.limite;
        this.movimentos = builder.movimentos;
    }

    public void addMovimento(Movimento movimento) {
        this.movimentos.add(movimento);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void suspender() {
        situacao = SUSPENSO;
    }

    public void debitar(Movimento movimento) {
        
        if (!isLimiteParaOMovimento(movimento.getValorMovimento())) {
            throw new IllegalArgumentException("Não existe saldo para efetuar o movimento.");
        }
        
        saldoAlocado += movimento.getValorMovimento();
        this.addMovimento(movimento);
    }

    public void creditar(Movimento movimento) {
        saldoAlocado -= movimento.getValorMovimento();
        this.addMovimento(movimento);
    }

    public boolean isDisponivel() {
        return ABERTO.equals(situacao);
    }

    public boolean isLimiteParaOMovimento(double valorMovimento) {
        // TODO Auto-generated method stub
        if (valorMovimento > (this.limite - this.saldoAlocado)) {
            return false;
        } else {
            return true;
        }
    }

    public double getCreditoEmergencial() {
        return this.limite * 0.50;
    }

    public static class Builder {

        private ContaId id;

        private Empresa empresa;

        private Gerente gerente;

        private Situacao situacao;

        private final double maximoLimite = 15000;

        private double limite;

        private List<Movimento> movimentos;

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

        public Builder movimentos(ArrayList<Movimento> movimentos) {
            this.movimentos = movimentos;
            return this;
        }

        public Conta build() {
            return new Conta(this);
        }

        public Conta buildAsNew() {
            this.situacao = Situacao.ABERTO;
            this.limite = getLimiteInicial();
            this.movimentos = new ArrayList<>();
            return this.build();
        }

        public double getLimiteInicial() {
            if (empresa.getNumerosDeFuncionarios() * empresa.getValorMercadoEmpresa() > this.maximoLimite)
                return this.maximoLimite;

            return empresa.getNumerosDeFuncionarios() * empresa.getValorMercadoEmpresa();
        }
        
    }

   

}*/
