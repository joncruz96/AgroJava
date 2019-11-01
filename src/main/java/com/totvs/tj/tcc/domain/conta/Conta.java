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
    
    private final double limite;
    
    private double limiteEmergencial;
    
    private double saldoAlocado;    
    
    private final double porcentagemLimiteEmergencial = 50;
    
    public void validarSolicitacaoLimiteEmergencial(double valorEmergencial) {
        if (isLimiteJaSolicitado()) {
            throw new IllegalArgumentException("Já foi solicitado um limite");
        }else if(valorEmergencial > getMaximoLimiteEmergencial()) {
            throw new IllegalArgumentException("Valor do Limite Emergencial acima do permitido.");
        }    
    }
    
    public void adicionarLimiteEmergencial(double valorEmergencial) {
        this.validarSolicitacaoLimiteEmergencial(valorEmergencial);
        this.limiteEmergencial = valorEmergencial;        
    }
    
    public boolean isLimiteJaSolicitado() {
        return this.getLimiteEmergencial() > 0;
    }
    
    public double getMaximoLimiteEmergencial() {
        return this.getLimite() * (porcentagemLimiteEmergencial / 100);
    }

}


