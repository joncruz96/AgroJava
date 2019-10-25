package com.totvs.tj.tcc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.totvs.tj.tcc.domain.conta.Empresa;
import com.totvs.tj.tcc.domain.conta.EmpresaId;
import com.totvs.tj.tcc.domain.conta.Responsavel;
import com.totvs.tj.tcc.domain.conta.ResponsavelId;

public class EmpresaTest {

    private final EmpresaId idEmpresa = EmpresaId.generate();

    private final ResponsavelId idResponsavel = ResponsavelId.generate();
    
    private final double valorMercadoEmpresa = 10000.00;
    
    private final int numerosDeFuncionarios = 12;

    @Test
    public void aoCriarUmaEmpresa() throws Exception {

        // WHEN
        Responsavel responsavel = Responsavel.builder()
                .id(idResponsavel)                
            .build();
        
        Empresa empresa = Empresa.builder()
                .id(idEmpresa)
                .responsavel(responsavel)
                .numerosDeFuncionarios(numerosDeFuncionarios)
                .valorMercadoEmpresa(valorMercadoEmpresa)
            .build();

        // THEN
        assertNotNull(empresa);
      
        assertEquals(idEmpresa, empresa.getId());
        assertEquals(responsavel, empresa.getResponsavel());
        assertEquals(numerosDeFuncionarios, empresa.getNumerosDeFuncionarios());
        assertEquals(valorMercadoEmpresa, empresa.getValorMercadoEmpresa(), 0);
        
        
        assertEquals(idEmpresa.toString(), empresa.getId().toString());
        assertEquals(responsavel.toString(), empresa.getResponsavel().toString());
    }

}
