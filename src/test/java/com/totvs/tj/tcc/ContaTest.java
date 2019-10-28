package com.totvs.tj.tcc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.totvs.tj.tcc.domain.conta.Conta;
import com.totvs.tj.tcc.domain.conta.ContaId;
import com.totvs.tj.tcc.domain.conta.ContaRepository;
import com.totvs.tj.tcc.domain.conta.Empresa;
import com.totvs.tj.tcc.domain.conta.EmpresaId;
import com.totvs.tj.tcc.domain.conta.Gerente;
import com.totvs.tj.tcc.domain.conta.GerenteId;
import com.totvs.tj.tcc.domain.conta.Responsavel;
import com.totvs.tj.tcc.domain.conta.ResponsavelId;

public class ContaTest {

    private final ContaId idConta = ContaId.generate();

    private final EmpresaId idEmpresa = EmpresaId.generate();

    private final ResponsavelId idResponsavel = ResponsavelId.generate();
    
    private final GerenteId idGerente = GerenteId.generate();
    
    private final double valorMercadoEmpresaNormal = 1000.00;
    
    private final int numerosDeFuncionariosNormal = 12;
    
    private final double valorMercadoEmpresaLimite = 10000.00;
    
    private final int numerosDeFuncionariosLimite = 12;
    

    @Test
    public void aoCriarUmaConta() throws Exception {

        // WHEN
        Responsavel responsavel = Responsavel.builder()
                .id(idResponsavel)           
            .build();
        
        Gerente gerente = Gerente.builder()
                .id(idGerente)
                .build();
        
        Empresa empresa = Empresa.builder()
                .id(idEmpresa)
                .responsavel(responsavel)
                .numerosDeFuncionarios(numerosDeFuncionariosNormal)
                .valorMercadoEmpresa(valorMercadoEmpresaNormal)
            .build();
        
        Conta conta = Conta.builder()
                .id(idConta)
                .empresa(empresa)
                .gerente(gerente)
            .buildAsNew();

        // THEN
        assertNotNull(conta);
        
        assertEquals(idConta, conta.getId());
        assertEquals(empresa, conta.getEmpresa());
        assertEquals(gerente, conta.getGerente());
        assertEquals(numerosDeFuncionariosNormal * valorMercadoEmpresaNormal, conta.getLimite(), 0);
        
        assertEquals(idConta.toString(), conta.getId().toString());
        assertEquals(empresa.toString(), conta.getEmpresa().toString());
        assertEquals(gerente.toString(), conta.getGerente().toString());
    }
    
    @Test
    public void aoCriarUmaContaLimiteMaximo() throws Exception {

        // WHEN
        Responsavel responsavel = Responsavel.builder()
                .id(idResponsavel)           
            .build();
        
        Gerente gerente = Gerente.builder()
                .id(idGerente)
                .build();
        
        Empresa empresa = Empresa.builder()
                .id(idEmpresa)
                .responsavel(responsavel)
                .numerosDeFuncionarios(numerosDeFuncionariosLimite)
                .valorMercadoEmpresa(valorMercadoEmpresaLimite)
            .build();
        
        Conta conta = Conta.builder()
                .id(idConta)
                .empresa(empresa)
                .gerente(gerente)
            .buildAsNew();

        // THEN
        assertNotNull(conta);
        
        assertEquals(idConta, conta.getId());
        assertEquals(empresa, conta.getEmpresa());
        assertEquals(gerente, conta.getGerente());
        assertEquals(conta.getMaximoLimite(), conta.getLimite(), 0);

        assertEquals(idConta.toString(), conta.getId().toString());
        assertEquals(empresa.toString(), conta.getEmpresa().toString());
        assertEquals(gerente.toString(), conta.getGerente().toString());
    }

    /*
   @Test
    public void aoSolicitarAberturaConta() throws Exception {

        // GIVEN
        ContaRepository repository = new ContaRepositoryMock();
        ContaApplicationService service = new ContaApplicationService(repository);
        
        Responsavel responsavel = Responsavel.builder()
                .id(idResponsavel)                           
            .build();
        
        Empresa empresa = Empresa.builder()
                .id(idEmpresa)
                .responsavel(responsavel)
                .numerosDeFuncionarios(numerosDeFuncionarios)
                .valorMercadoEmpresa(valorMercadoEmpresa)
            .build();
        
        AbrirContaCommand cmd = AbrirContaCommand.builder()
                .empresa(empresa)
                .responsavel(idResponsavel)
                .build();

        // WHEN
        ContaId idConta = service.handle(cmd);

        // THEN
        assertNotNull(idConta);
    }
/*
    @Test
    public void supenderUmaContaExistente() throws Exception {

        // GIVEN
        SuspenderContaCommand cmd = SuspenderContaCommand.from(idConta);

        ContaRepository repository = new ContaRepositoryMock();
        ContaApplicationService service = new ContaApplicationService(repository);

        repository.save(Conta.builder()
                .id(idConta)
                .empresa(idEmpresa)
                .responsavel(idResponsavel)
            .build());
        
        // WHEN
        service.handle(cmd);

        // THEN
        assertFalse(repository.getOne(idConta).isDisponivel());
    }
    
    @Test(expected = NullPointerException.class)
    public void aoNaoEncontrarContaParaSuspender() throws Exception {
        
        // GIVEN
        SuspenderContaCommand cmd = SuspenderContaCommand.from(idConta);
        
        ContaRepository repository = new ContaRepositoryMock();
        ContaApplicationService service = new ContaApplicationService(repository);

        // WHEN
        service.handle(cmd);
        
        // THEN
        assertTrue("Não deve chegar aqui...", false);
    }
     */
    static class ContaRepositoryMock implements ContaRepository {

        private final Map<ContaId, Conta> contas = new LinkedHashMap<>();

        @Override
        public void save(Conta conta) {
            contas.put(conta.getId(), conta);
        }

        @Override
        public Conta getOne(ContaId id) {
            return contas.get(id);
        }
    }
}
