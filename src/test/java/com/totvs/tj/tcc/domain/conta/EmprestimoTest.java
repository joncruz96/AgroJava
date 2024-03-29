package com.totvs.tj.tcc.domain.conta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Description;

import com.totvs.tj.tcc.app.AprovarEmprestimoCommand;
import com.totvs.tj.tcc.app.EmprestimoApplicationService;
import com.totvs.tj.tcc.app.QuitarEmprestimoCommand;
import com.totvs.tj.tcc.app.SolicitarEmprestimoCommand;

public class EmprestimoTest {

    private final EmpresaId idEmpresa = EmpresaId.generate();

    private final EmprestimoId idEmprestimo = EmprestimoId.generate();

    private final double valorMercadoEmpresa = 1000.00;

    private final int numerosDeFuncionarios = 12;

    private final String cnpjEmpresa = "90.090.876/0001-01";

    private final String cpfResponsavel = "099.111.333-09";

    private final String nomeResponsavel = "Nome do Responsável";

    private Empresa empresa;

    private double valorEmprestimoQuitado = 1000;

    private double valorEmprestimoLiberado = 1000;

    private double valorEmprestimoSemLimite = 20000;

    private double valorEmprestimoAguardandoAprovacao = 3100;

    private double limiteEmpresa;

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

        this.limiteEmpresa = this.empresa.getContaLimite();
    }

    @Test
    @Description("Ao solicitar emprestimo com empresa suspensa.")
    public void aoSolicitarEmprestimoComEmpresaSuspensa() throws Exception {

        //GIVEN
        EmprestimoRepository repository = new EmprestimoRepositoryMock();

        EmprestimoApplicationService service = EmprestimoApplicationService.builder()
                .repository(repository).build();

        empresa.suspender();

        SolicitarEmprestimoCommand cmd = SolicitarEmprestimoCommand.builder()
                .empresa(empresa)
                .idEmprestimo(idEmprestimo)
                .valor(valorEmprestimoLiberado)
                .situacao(SituacaoEmprestimo.PENDENTE)
                .build();

        //WHEN
        EmprestimoId emprestimoId = service.handle(cmd);

        // THEN
        Emprestimo emprestimoSaved = repository.getOne(emprestimoId);

        emprestimoSaved.reprovar();

        assertNotNull(emprestimoSaved);
        assertEquals(SituacaoEmprestimo.REPROVADO, emprestimoSaved.getSituacao());
        assertEquals(SituacaoEmprestimo.REPROVADO, SituacaoEmprestimo.REPROVADO.proximaSituacao(emprestimoSaved));

    }

    @Test
    @Description("Ao solicitar emprestimo sem limite.")
    public void aoSolicitarEmprestimoSemLimite() throws Exception {

        //GIVEN
        EmprestimoRepository repository = new EmprestimoRepositoryMock();

        EmprestimoApplicationService service = EmprestimoApplicationService.builder()
                .repository(repository).build();

        SolicitarEmprestimoCommand cmd = SolicitarEmprestimoCommand.builder()
                .empresa(empresa)
                .idEmprestimo(idEmprestimo)
                .valor(valorEmprestimoSemLimite)
                .situacao(SituacaoEmprestimo.PENDENTE)
                .build();

        //WHEN
        EmprestimoId emprestimoId = service.handle(cmd);

        // THEN
        Emprestimo emprestimoSaved = repository.getOne(emprestimoId);

        emprestimoSaved.semLimiteDisponivel();

        assertNotNull(emprestimoSaved);
        assertEquals(SituacaoEmprestimo.SEM_LIMITE_DISPONIVEL, emprestimoSaved.getSituacao());
        assertEquals(SituacaoEmprestimo.SEM_LIMITE_DISPONIVEL,
                SituacaoEmprestimo.SEM_LIMITE_DISPONIVEL.proximaSituacao(emprestimoSaved));
    }

    @Test
    @Description("Ao solicitar emprestimo Aguardando Aprovação.")
    public void aoSolicitarEmprestimoAguardandoAprovacao() throws Exception {

        //GIVEN
        EmprestimoRepository repository = new EmprestimoRepositoryMock();

        EmprestimoApplicationService service = EmprestimoApplicationService.builder()
                .repository(repository).build();

        SolicitarEmprestimoCommand cmd = SolicitarEmprestimoCommand.builder()
                .empresa(empresa)
                .idEmprestimo(idEmprestimo)
                .valor(valorEmprestimoAguardandoAprovacao)
                .situacao(SituacaoEmprestimo.PENDENTE)
                .build();

        //WHEN
        EmprestimoId emprestimoId = service.handle(cmd);

        // THEN
        Emprestimo emprestimoSaved = repository.getOne(emprestimoId);

        emprestimoSaved.aguardarAprovacao();

        assertNotNull(emprestimoSaved);
        assertEquals(SituacaoEmprestimo.AGUARDANDO_APROVACAO, emprestimoSaved.getSituacao());
    }

    @Test
    @Description("Ao solicitar emprestimo liberar emprestimo.")
    public void aoSolicitarEmprestimoLiberarEmprestimo() throws Exception {

        //GIVEN
        EmprestimoRepository repository = new EmprestimoRepositoryMock();

        EmprestimoApplicationService service = EmprestimoApplicationService.builder()
                .repository(repository).build();

        SolicitarEmprestimoCommand cmd = SolicitarEmprestimoCommand.builder()
                .empresa(empresa)
                .idEmprestimo(idEmprestimo)
                .valor(valorEmprestimoLiberado)
                .situacao(SituacaoEmprestimo.PENDENTE)
                .build();

        //WHEN
        EmprestimoId emprestimoId = service.handle(cmd);

        // THEN
        Emprestimo emprestimoSaved = repository.getOne(emprestimoId);

        emprestimoSaved.liberarEmprestimo();

        assertNotNull(emprestimoSaved);
        assertEquals(empresa.getSaldoAlocado(), valorEmprestimoLiberado, 2);
        assertEquals(emprestimoId.toString(), emprestimoSaved.getId().toString());
        assertEquals(SituacaoEmprestimo.LIBERADO, emprestimoSaved.getSituacao());
        assertEquals(this.limiteEmpresa - valorEmprestimoLiberado, empresa.getContaLimiteAtual(), 2);

    }

    @Test
    @Description("Ao aprovar emprestimo.")
    public void aoAprovarEmprestimo() throws Exception {

        //GIVEN
        Emprestimo emprestimo = Emprestimo.builder()
                .empresa(empresa)
                .id(idEmprestimo)
                .valor(valorEmprestimoLiberado)
                .situacao(SituacaoEmprestimo.AGUARDANDO_APROVACAO)
                .build();

        EmprestimoRepository repository = new EmprestimoRepositoryMock();

        EmprestimoApplicationService service = EmprestimoApplicationService.builder()
                .repository(repository).build();

        AprovarEmprestimoCommand cmd = AprovarEmprestimoCommand.builder()
                .emprestimo(emprestimo)
                .build();

        //WHEN
        EmprestimoId emprestimoId = service.handle(cmd);

        // THEN
        Emprestimo emprestimoSaved = repository.getOne(emprestimoId);

        System.out.println(empresa.getSaldoAlocado());

        assertNotNull(emprestimoSaved);
        assertEquals(empresa.getSaldoAlocado(), valorEmprestimoLiberado, 2);
        assertEquals(emprestimoId.toString(), emprestimoSaved.getId().toString());
        assertEquals(SituacaoEmprestimo.LIBERADO, emprestimoSaved.getSituacao());
        assertEquals(this.limiteEmpresa - valorEmprestimoLiberado, empresa.getContaLimiteAtual(), 2);
    }

    @Test
    @Description("Ao quitar emprestimo.")
    public void aoQuitarEmprestimo() throws Exception {

        //GIVEN
        Emprestimo emprestimo = Emprestimo.builder()
                .empresa(empresa)
                .id(idEmprestimo)
                .valor(valorEmprestimoQuitado)
                .situacao(SituacaoEmprestimo.LIBERADO)
                .build();

        emprestimo.liberarEmprestimo();

        EmprestimoRepository repository = new EmprestimoRepositoryMock();

        EmprestimoApplicationService service = EmprestimoApplicationService.builder()
                .repository(repository).build();

        QuitarEmprestimoCommand cmd = QuitarEmprestimoCommand.builder()
                .emprestimo(emprestimo)
                .valor(valorEmprestimoQuitado)
                .build();

        //WHEN
        EmprestimoId emprestimoId = service.handle(cmd);

        // THEN
        Emprestimo emprestimoSaved = repository.getOne(emprestimoId);

        System.out.println(empresa.getSaldoAlocado());

        assertNotNull(emprestimoSaved);
        assertEquals(0, empresa.getSaldoAlocado(), 2);
        assertEquals(SituacaoEmprestimo.QUITADO, emprestimoSaved.getSituacao());
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
