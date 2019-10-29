package com.totvs.tj.tcc.app;

import org.springframework.stereotype.Service;

import com.totvs.tj.tcc.domain.conta.Conta;
import com.totvs.tj.tcc.domain.conta.ContaId;
import com.totvs.tj.tcc.domain.conta.ContaRepository;
import com.totvs.tj.tcc.domain.conta.Movimento;

@Service
public class ContaApplicationService {
    
    private ContaRepository repository;
    
    public ContaApplicationService(ContaRepository repository) {
        this.repository = repository;
    }
    
    public ContaId handle(AbrirContaCommand cmd) {
        
        ContaId idConta = ContaId.generate();
       
        Conta conta = Conta.builder()
                .id(idConta)
                .empresa(cmd.getEmpresa())                
            .build();
        
        repository.save(conta);
        
        return idConta; 
    }
   
    public void handle(SuspenderContaCommand cmd) {
        
        Conta conta = repository.getOne(cmd.getConta());
        
        conta.suspender();
        
        repository.save(conta);
    }
    
    public void handle(DebitarContaCommand cmd) {
        
        Conta conta = cmd.getConta();
        Movimento movimento = cmd.getMovimento();
        
        conta.debitar(movimento);        
        repository.save(conta);
    }
    
    public void handle(CreditarContaCommand cmd) {
        
        Conta conta = cmd.getConta();
        Movimento movimento = cmd.getMovimento();
        
        conta.creditar(movimento);        
        repository.save(conta);
    }
    
}
