package com.totvs.tj.tcc.domain.conta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;


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

        // WHEN
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
        
        // THEN
        assertNotNull(empresa);
        
        assertEquals(idEmpresa, empresa.getId());
        assertEquals(responsavel, empresa.getResponsavel());
        

    }
    
    @Test
    public void aoCriarUmaContaParaEmpresaComLimiteMaximo() throws Exception {

        // WHEN
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
        
        // THEN
        assertNotNull(empresa);
        
        assertEquals(idEmpresa, empresa.getId());
        assertEquals(responsavel, empresa.getResponsavel());
        

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
    /*
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
    */
}
