package com.totvs.tj.tcc;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.totvs.tj.tcc.app.ContaApplicationService;
import com.totvs.tj.tcc.app.CreditarContaCommand;
import com.totvs.tj.tcc.app.DebitarContaCommand;
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

public class EmprestimoTest {
    
    private final ContaId idConta = ContaId.generate();
    private final MovimentoId idMovimento = MovimentoId.generate();
    private final EmpresaId idEmpresa = EmpresaId.generate();

    private final ResponsavelId idResponsavel = ResponsavelId.generate();
    
    private final GerenteId idGerente = GerenteId.generate();
    
    private Gerente gerente;
    
    private Empresa empresa;
    
    private final double valorMercadoEmpresaNormal = 1000.00;
    
    private final int numerosDeFuncionariosNormal = 12;
    
    private final double valorMovimento = 120.00;
    private final double valorDevolvido = 100.00;
    
    private final double valorMovimentoAcimaLimite = 120000.00;
    
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
     public void aoSolicitarEmprestimo() throws Exception {

         // GIVEN
        Conta conta = Conta.builder()
                .id(idConta)
                .empresa(empresa)
                .gerente(gerente)
            .buildAsNew();
        
        Movimento movimentoDebito = Movimento.builder()
                .id(idMovimento)
                .tipo(TipoMovimento.EMPRESTIMO)
                .valorMovimento(valorMovimento)
            .build();
        
        Movimento movimentoCredito = Movimento.builder()
                .id(idMovimento)
                .tipo(TipoMovimento.DEVOLUCAO)
                .valorMovimento(valorDevolvido)
            .build();
        
        DebitarContaCommand cmdDebito          = DebitarContaCommand.builder()
                                                .conta(conta)
                                                .movimento(movimentoDebito)
                                                .build();
        
        CreditarContaCommand cmdCredito          = CreditarContaCommand.builder()
                .conta(conta)
                .movimento(movimentoCredito)
                .build();
                                                        
         ContaRepository repository      = new ContaRepositoryMock();
         ContaApplicationService service = new ContaApplicationService(repository);

         // WHEN
         service.handle(cmdDebito);
         service.handle(cmdCredito);

         // THEN
         assertEquals(valorMovimento - valorDevolvido, conta.getSaldoAlocado(), 0);
     }
    

    @Test(expected = IllegalArgumentException.class)
    public void aoSolicitarEmprestimoSemSalfo() throws Exception {

         // GIVEN
        Conta conta = Conta.builder()
                .id(idConta)
                .empresa(empresa)
                .gerente(gerente)
            .buildAsNew();
        
        Movimento movimentoDebito = Movimento.builder()
                .id(idMovimento)
                .tipo(TipoMovimento.EMPRESTIMO)
                .valorMovimento(valorMovimentoAcimaLimite)
            .build();
     
        
        DebitarContaCommand cmdDebito          = DebitarContaCommand.builder()
                                                .conta(conta)
                                                .movimento(movimentoDebito)
                                                .build();
        
       
                                                        
         ContaRepository repository      = new ContaRepositoryMock();
         ContaApplicationService service = new ContaApplicationService(repository);

         // WHEN
         service.handle(cmdDebito);
     

         // THEN
         //assertTrue("Não deve terminar a execução.", false);
    }
    
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
