package com.totvs.tj.tcc.domain.conta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.totvs.tj.tcc.app.AbrirContaCommand;
import com.totvs.tj.tcc.app.EmpresaApplicationService;


public class ContaTest {
    
    
    private final EmpresaId idEmpresa = EmpresaId.generate();
        
    private final double valorMercadoEmpresaNormal = 1000.00;    
    private final int numerosDeFuncionariosNormal = 12;
    
    private final double valorMercadoEmpresaLimite = 10000.00;
    private final int numerosDeFuncionariosLimite = 12;
    
    private final String cnpjEmpresa = "90.090.876/0001-01";
    
    private final String cpfResponsavel = "099.111.333-09";
    private final String nomeResponsavel = "Nome do Responsável";
    

    @Test
    public void aoCriarUmaContaParaEmpresa() throws Exception {

        // GIVEN
        Responsavel responsavel = Responsavel.builder()
                .cpf(cpfResponsavel)
                .nome(nomeResponsavel)
            .build();

        Empresa empresa = Empresa.builder()
                .id(idEmpresa)
                .responsavel(responsavel)
                .cnpj(cnpjEmpresa)                
                .numerosDeFuncionarios(numerosDeFuncionariosNormal)
                .valorMercadoEmpresa(valorMercadoEmpresaNormal)
            .buildAsNew();
        
        EmpresaRepository repository        = new EmpresaRepositoryMock();
        EmpresaApplicationService service   = EmpresaApplicationService.builder()
                .repository(repository).build();
        AbrirContaCommand cmd = AbrirContaCommand.builder()
                .empresa(empresa)
                .build();
        
        //WHEN
        EmpresaId empresaId = service.handle(cmd);
        Empresa empresaSaved = repository.getOne(empresaId);
        
        // THEN 
        assertNotNull(empresaId);
        assertNotNull(empresaSaved);
        
        assertEquals(empresaId, empresaSaved.getId());
        assertEquals(responsavel, empresaSaved.getResponsavel());
        assertEquals(valorMercadoEmpresaNormal * numerosDeFuncionariosLimite, empresaSaved.getContaLimite(), 0);
        assertEquals(0, empresaSaved.getContaSaldo(), 0);
        

    }
    
    @Test
    public void aoCriarUmaContaParaEmpresaComLimiteMaximo() throws Exception {

        // GIVEN
        Responsavel responsavel = Responsavel.builder()
                .cpf(cpfResponsavel)
                .nome(nomeResponsavel)
            .build();

        Empresa empresa = Empresa.builder()
                .id(idEmpresa)
                .responsavel(responsavel)
                .cnpj(cnpjEmpresa)                
                .numerosDeFuncionarios(numerosDeFuncionariosLimite)
                .valorMercadoEmpresa(valorMercadoEmpresaLimite)
            .buildAsNew();
        
        EmpresaRepository repository        = new EmpresaRepositoryMock();
        EmpresaApplicationService service   = EmpresaApplicationService.builder()
                .repository(repository).build();
        AbrirContaCommand cmd = AbrirContaCommand.builder()
                .empresa(empresa)
                .build();
        
        //WHEN
        EmpresaId empresaId = service.handle(cmd);
        Empresa empresaSaved = repository.getOne(empresaId);
       
        // THEN
        assertNotNull(empresaId);
        assertNotNull(empresaSaved);
        
        assertEquals(empresaId, empresaSaved.getId());
        assertEquals(responsavel, empresaSaved.getResponsavel());
        assertEquals(empresaSaved.getMaximoLimite(), empresaSaved.getContaLimite(), 0);
        assertEquals(0, empresaSaved.getContaSaldo(), 0);

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
   
    static class EmpresaRepositoryMock implements EmpresaRepository {

        private final Map<EmpresaId, Empresa> empresas = new LinkedHashMap<>();

        @Override
        public void save(Empresa empresa) {
            empresas.put(empresa.getId(), empresa);
        }

        @Override
        public Empresa getOne(EmpresaId id) {
            return empresas.get(id);
        }
    }
    
}
