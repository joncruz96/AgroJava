package com.totvs.tj.tcc.app;

import org.springframework.stereotype.Service;

import com.totvs.tj.tcc.domain.conta.ContaRepository;

@Service
public class ContaApplicationService {
    
    private ContaRepository repository;
    
    public ContaApplicationService(ContaRepository repository) {
        this.repository = repository;
    }
    /*
    public ContaId handle(AbrirContaCommand cmd) {
        
        
        
        repository.save(conta);
        
        return idConta; 
    }
   
    public void handle(SuspenderContaCommand cmd) {
        
        Conta conta = repository.getOne(cmd.getConta());
        
        conta.suspender();
        
        repository.save(conta);
    }
    */
  
}
