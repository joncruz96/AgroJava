package com.totvs.tj.tcc.domain.conta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.totvs.tj.tcc.app.EmprestimoApplicationService;
import com.totvs.tj.tcc.app.QuitarEmprestimoCommand;
import com.totvs.tj.tcc.app.SolicitarEmprestimoCommand;
import com.totvs.tj.tcc.domain.conta.Emprestimo.SituacaoEmprestimo;

public class EmprestimoTest {

    private final EmpresaId idEmpresa = EmpresaId.generate();
    private final EmprestimoId idEmprestimo = EmprestimoId.generate();

    private final double valorMercadoEmpresa = 1000.00;
    private final int numerosDeFuncionarios = 12;

    private final String cnpjEmpresa = "90.090.876/0001-01";

    private final String cpfResponsavel = "099.111.333-09";
    private final String nomeResponsavel = "Nome do Responsável";

    private Empresa empresa;
    
    private double valorEmprestimo = 1000;

    @Before
    public void setup() {

        Responsavel responsavel = Responsavel.builder()
                .cpf(cpfResponsavel)
                .nome(nomeResponsavel)
                .build();

        this.empresa = Empresa.builder()
                .id(idEmpresa)
                .responsavel(responsavel)
                .cnpj(cnpjEmpresa)
                .numerosDeFuncionarios(numerosDeFuncionarios)
                .valorMercadoEmpresa(valorMercadoEmpresa)
                .buildAsNew();

    }

    @Test
    public void aoSolicitarEmprestimo() throws Exception{
        
        //GIVEN
        EmprestimoRepository repository        = new EmprestimoRepositoryMock();
        EmprestimoApplicationService service   = EmprestimoApplicationService.builder()
                .repository(repository).build();
        SolicitarEmprestimoCommand cmd = SolicitarEmprestimoCommand.builder()
                .empresa(empresa)
                .idEmprestimo(idEmprestimo)
                .valor(valorEmprestimo)
                .situacao(SituacaoEmprestimo.PENDENTE)
                .build();
        
        //WHEN
        EmprestimoId emprestimoId = service.handle(cmd);
        
        // THEN
        Emprestimo emprestimoSaved = repository.getOne(emprestimoId);
        
        assertNotNull(emprestimoSaved);
        assertEquals(empresa.getSaldoAlocado(), valorEmprestimo, 2);
        assertEquals(emprestimoSaved.getSituacao(), SituacaoEmprestimo.LIBERADO);
    }
    
    @Test
    public void aoQuitarEmprestimo() throws Exception{
        
        //GIVEN
        Emprestimo emprestimo = Emprestimo.builder()
                .empresa(empresa)
                .id(idEmprestimo)
                 .valor(valorEmprestimo)
                 .situacao(SituacaoEmprestimo.LIBERADO)
                 .build();
        
        emprestimo.liberarEmprestimo();
        
        EmprestimoRepository repository        = new EmprestimoRepositoryMock();
        EmprestimoApplicationService service   = EmprestimoApplicationService.builder()
                .repository(repository).build();
        QuitarEmprestimoCommand cmd = QuitarEmprestimoCommand.builder()
                .emprestimo(emprestimo)
                .valor(valorEmprestimo)
                .build();
        
        //WHEN
        EmprestimoId emprestimoId = service.handle(cmd);
        
        // THEN
        Emprestimo emprestimoSaved = repository.getOne(emprestimoId);
        
        assertNotNull(emprestimoSaved);
        assertEquals(empresa.getSaldoAlocado(), 0, 2);
        assertEquals(emprestimoSaved.getSituacao(), SituacaoEmprestimo.QUITADO);
    }
    
    static class EmprestimoRepositoryMock implements EmprestimoRepository {

        private final Map<EmprestimoId, Emprestimo> emprestimos = new LinkedHashMap<>();

        @Override
        public void save(Emprestimo emprestimo) {
            emprestimos.put(emprestimo.getId(), emprestimo);
        }

        @Override
        public Emprestimo getOne(EmprestimoId id) {
            return emprestimos.get(id);
        }
    }

}
