package com.totvs.tj.tcc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.totvs.tj.tcc.app.AbrirContaCommand;
import com.totvs.tj.tcc.app.ContaApplicationService;
import com.totvs.tj.tcc.domain.conta.Conta;
import com.totvs.tj.tcc.domain.conta.ContaId;
import com.totvs.tj.tcc.domain.conta.ContaRepository;
import com.totvs.tj.tcc.domain.conta.Empresa;
import com.totvs.tj.tcc.domain.conta.EmpresaId;
import com.totvs.tj.tcc.domain.conta.Responsavel;
import com.totvs.tj.tcc.domain.conta.ResponsavelId;

public class ContaTest {

    private final ContaId idConta = ContaId.generate();

    private final EmpresaId idEmpresa = EmpresaId.generate();

    private final ResponsavelId idResponsavel = ResponsavelId.generate();
    
    private final double valorMercadoEmpresa = 10000.00;
    
    private final int numerosDeFuncionarios = 12;
    

    @Test
    public void aoCriarUmaConta() throws Exception {

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
        
        Conta conta = Conta.builder()
                .id(idConta)
                .empresa(empresa)
                .responsavel(idResponsavel)
            .build();

        // THEN
        assertNotNull(conta);

        assertEquals(idConta, conta.getId());
        assertEquals(empresa, conta.getEmpresa());
        assertEquals(idResponsavel, conta.getResponsavel());

        assertEquals(idConta.toString(), conta.getId().toString());
        assertEquals(empresa.toString(), conta.getEmpresa().toString());
        assertEquals(idResponsavel.toString(), conta.getResponsavel().toString());
    }

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
