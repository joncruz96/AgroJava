package com.totvs.tj.tcc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.totvs.tj.tcc.domain.conta.Conta;
import com.totvs.tj.tcc.domain.conta.ContaId;
import com.totvs.tj.tcc.domain.conta.ContaRepository;
import com.totvs.tj.tcc.domain.conta.Empresa;
import com.totvs.tj.tcc.domain.conta.EmpresaId;
import com.totvs.tj.tcc.domain.conta.Gerente;
import com.totvs.tj.tcc.domain.conta.GerenteId;
import com.totvs.tj.tcc.domain.conta.Movimento;
import com.totvs.tj.tcc.domain.conta.MovimentoId;
import com.totvs.tj.tcc.domain.conta.Responsavel;
import com.totvs.tj.tcc.domain.conta.ResponsavelId;
import com.totvs.tj.tcc.domain.conta.TipoMovimento;

public class MovimentacaoTest {

    private final ContaId idConta = ContaId.generate();

    private final EmpresaId idEmpresa = EmpresaId.generate();

    private final ResponsavelId idResponsavel = ResponsavelId.generate();
    
    private final GerenteId idGerente = GerenteId.generate();
    
    private final double valorMercadoEmpresaNormal = 1000.00;
    
    private final int numerosDeFuncionariosNormal = 12;
    
    private final MovimentoId idMovimento = MovimentoId.generate();
    
    private final double valorMovimento = 120.00;
    
    private final double valorExcedente = 100000.00;
        
    private Gerente gerente;
    
    private Empresa empresa;
    
    @Before
    public void setup() {
        
        Responsavel responsavel = Responsavel.builder()
                .id(idResponsavel)           
            .build();
        
        this.gerente = Gerente.builder()
                .id(idGerente)
                .build();
        
        this.empresa = Empresa.builder()
                .id(idEmpresa)
                .responsavel(responsavel)
                .numerosDeFuncionarios(numerosDeFuncionariosNormal)
                .valorMercadoEmpresa(valorMercadoEmpresaNormal)
            .build();       
 
    }
    
    @Test
    public void aoCriarUmMovimento() throws Exception {

        // given
        Conta conta = Conta.builder()
                .id(idConta)
                .empresa(empresa)
                .gerente(gerente)
            .buildAsNew();
                     
        Movimento movimento = Movimento.builder()
                .id(idMovimento)
                .conta(conta)
                .tipo(TipoMovimento.EMPRESTIMO)
                .valorMovimento(valorMovimento)
            .build();
        
     // THEN
        
        
        //assertTrue(conta.isLimiteParaOMovimento(valorMovimento));
                        
    }
    
    @Test
    public void aoCriarUmMovimentoSemLimite() throws Exception {

        // WHEN
        Conta conta = Conta.builder()
                .id(idConta)
                .empresa(empresa)
                .gerente(gerente)
            .buildAsNew();
        
        // THEN
        assertFalse(conta.isLimiteParaOMovimento(valorExcedente));
                                
    }
    
    @Test
    public void aoCriarUmMovimentoComContaSuspensa() throws Exception {

        // WHEN
        Conta conta = Conta.builder()
                .id(idConta)
                .empresa(empresa)
                .gerente(gerente)
            .buildAsNew();
        
               
       assertFalse(conta.isDisponivel());                  
                                
    }
    
    @Test
    public void aoCriarUmMovimentoComAprovacao() throws Exception {

        // WHEN
        Conta conta = Conta.builder()
                .id(idConta)
                .empresa(empresa)
                .gerente(gerente)
            .buildAsNew();
       
        
        Movimento movimento = Movimento.builder()
                .id(idMovimento)
                .conta(conta)
                .tipo(TipoMovimento.EMPRESTIMO)
                .valorMovimento(valorMovimento)
            .build();
        
     // THEN
        assertNotNull(movimento);
        
        
        assertTrue(conta.isLimiteParaOMovimento(valorMovimento));
                  
                                
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
