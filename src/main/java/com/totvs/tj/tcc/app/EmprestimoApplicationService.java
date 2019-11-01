package com.totvs.tj.tcc.app;

import org.springframework.stereotype.Service;

import com.totvs.tj.tcc.domain.conta.Emprestimo;
import com.totvs.tj.tcc.domain.conta.EmprestimoId;
import com.totvs.tj.tcc.domain.conta.EmprestimoRepository;

import lombok.Builder;

@Service
@Builder
public class EmprestimoApplicationService {
    
    private EmprestimoRepository repository;
    
    public EmprestimoApplicationService(EmprestimoRepository repository) {
        this.repository = repository;
    }
    
    public EmprestimoId handle(SolicitarEmprestimoCommand cmd) {
        Emprestimo emprestimo = cmd.getEmprestimo();
       
        emprestimo.solicitarLiberacaoEmprestimo();                    
        
        repository.save(emprestimo);
        
        return emprestimo.getId();
    }
   /*
    public void handle(SuspenderContaCommand cmd) {
        
        Conta conta = repository.getOne(cmd.getConta());
        
        conta.suspender();
        
        repository.save(conta);
    }
    */
  
}
