package com.totvs.tj.tcc;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.totvs.tj.tcc.domain.conta.Conta;
import com.totvs.tj.tcc.domain.conta.ContaId;
import com.totvs.tj.tcc.domain.conta.Empresa;
import com.totvs.tj.tcc.domain.conta.EmpresaId;
import com.totvs.tj.tcc.domain.conta.Gerente;
import com.totvs.tj.tcc.domain.conta.GerenteId;
import com.totvs.tj.tcc.domain.conta.Movimento;
import com.totvs.tj.tcc.domain.conta.MovimentoId;
import com.totvs.tj.tcc.domain.conta.Responsavel;
import com.totvs.tj.tcc.domain.conta.ResponsavelId;
import com.totvs.tj.tcc.domain.conta.TipoMovimento;

public class CreditoEmergencialTest {

    private final ContaId idConta = ContaId.generate();

    private final EmpresaId idEmpresa = EmpresaId.generate();

    private final GerenteId idGerente = GerenteId.generate();

    private final MovimentoId idMovimento = MovimentoId.generate();

    private final ResponsavelId idResponsavel = ResponsavelId.generate();

    private Empresa empresa;

    private Gerente gerente;

    private final int numerosDeFuncionariosNormal = 12;

    private final double valorMercadoEmpresaNormal = 1000.00;

    private final double limiteEmergencialSuperior = 7000.00;

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
    public void aoSolicitarCreditoEmergencial() throws Exception {

        Conta conta = Conta.builder()
                .id(idConta)
                .empresa(empresa)
                .gerente(gerente)
                .buildAsNew();

        Movimento movimentoDebito = Movimento.builder()
                .id(idMovimento)
                .tipo(TipoMovimento.EMPRESTIMO)
                .valorMovimento(conta.getCreditoEmergencial())
                .build();

        assertEquals(conta.getCreditoEmergencial(), movimentoDebito.getValorMovimento(), 0);

    }

/*    @Test
    public void aoSolicitarCreditoEmergencialSuperior() throws Exception {

        Conta conta = Conta.builder()
                .id(idConta)
                .empresa(empresa)
                .gerente(gerente)
                .buildAsNew();

        assertNotNull(conta);
        assertEquals("Limite de Crédito Emergencial maior que o permitido.",
                limiteEmergencialSuperior, conta.getCreditoEmergencial(), 0);

    }
*/
}