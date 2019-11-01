package com.totvs.tj.tcc.app;

import org.springframework.stereotype.Service;

import com.totvs.tj.tcc.domain.conta.Empresa;
import com.totvs.tj.tcc.domain.conta.EmpresaId;
import com.totvs.tj.tcc.domain.conta.EmpresaRepository;

import lombok.Builder;

@Service
@Builder
public class EmpresaApplicationService {
    
    private EmpresaRepository repository;
    
    public EmpresaApplicationService(EmpresaRepository repository) {
        this.repository = repository;
    }
    
    public EmpresaId handle(AbrirContaCommand cmd) {
        
        Empresa empresa = Empresa.builder()
                .id(cmd.getIdEmpresa())
                .responsavel(cmd.getResponsavel())
                .cnpj(cmd.getCnpj())                
                .numerosDeFuncionarios(cmd.getNumeroDeFuncionario())
                .valorMercadoEmpresa(cmd.getValorMercadoEmpresa())
            .buildAsNew();
        
        
        repository.save(empresa);
        
        return empresa.getId();
    }
   /*
    public void handle(SuspenderContaCommand cmd) {
        
        Conta conta = repository.getOne(cmd.getConta());
        
        conta.suspender();
        
        repository.save(conta);
    }
    */
  
}
