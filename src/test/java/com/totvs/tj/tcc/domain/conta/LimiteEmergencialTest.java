package com.totvs.tj.tcc.domain.conta;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.totvs.tj.tcc.app.EmpresaApplicationService;
import com.totvs.tj.tcc.app.LimiteEmergencialCommand;

public class LimiteEmergencialTest {

    private final EmpresaId idEmpresa = EmpresaId.generate();

    private final double valorMercadoEmpresa = 1000.00;
    private final double limiteEmergencial = 1000.00;
    private final double limiteEmergencialAcimaPermitido = 1000000;
    private final int numerosDeFuncionarios = 12;

    private final String cnpjEmpresa = "90.090.876/0001-01";

    private final String cpfResponsavel = "099.111.333-09";
    private final String nomeResponsavel = "Nome do Responsável";
    private double limiteEmpresa;
    private Empresa empresa;
    

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
        this.limiteEmpresa =  this.empresa.getContaLimite();

    }

    @Test
    public void aoSolicitarLimiteEmergencial() throws Exception{
        
        
        // GIVEN
        LimiteEmergencialCommand cmd = LimiteEmergencialCommand.builder()
                                       .empresaId(idEmpresa)
                                       .valorEmergencial(limiteEmergencial)
                                       .build();
        
        EmpresaRepository repository = new EmpresaRepositoryMock();
        EmpresaApplicationService service = new EmpresaApplicationService(repository);

        repository.save(this.empresa);
        
        // WHEN
        service.handle(cmd);

        // THEN
        assertEquals(limiteEmergencial, repository.getOne(idEmpresa).getContaLimiteEmergencial(),  0 );
        assertEquals(limiteEmergencial + this.limiteEmpresa, repository.getOne(idEmpresa).getContaLimiteTotal(), 0 );      
        assertEquals(this.limiteEmpresa, repository.getOne(idEmpresa).getContaLimite(), 0 );      
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void aoSolicitarLimiteEmergencialPelaSegundaVez() throws Exception{
        
        
        // GIVEN
        LimiteEmergencialCommand cmd = LimiteEmergencialCommand.builder()
                                       .empresaId(idEmpresa)
                                       .valorEmergencial(limiteEmergencial)
                                       .build();
        
        EmpresaRepository repository = new EmpresaRepositoryMock();
        EmpresaApplicationService service = new EmpresaApplicationService(repository);

        repository.save(this.empresa);
        
        // WHEN
        service.handle(cmd);
        service.handle(cmd);
        
        // THEN
        //error.
      
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void aoSolicitarLimiteEmergencialAcimaDoPermitido() throws Exception{
        
        
        // GIVEN
        LimiteEmergencialCommand cmd = LimiteEmergencialCommand.builder()
                                       .empresaId(idEmpresa)
                                       .valorEmergencial(limiteEmergencialAcimaPermitido)
                                       .build();
        
        EmpresaRepository repository = new EmpresaRepositoryMock();
        EmpresaApplicationService service = new EmpresaApplicationService(repository);

        repository.save(this.empresa);
        
        // WHEN
        service.handle(cmd);    

        // THEN
        //error.
      
    }
    
    
    static class EmpresaRepositoryMock implements EmpresaRepository {

        private final Map<EmpresaId, Empresa> empresas = new LinkedHashMap<>();

        @Override
        public void save(Empresa empresa) {
            empresas.put(empresa.getId(), empresa);
        }
        
        @Override
        public void update(Empresa empresa) {
            empresas.replace(empresa.getId(), empresa);
        }

        @Override
        public Empresa getOne(EmpresaId id) {
            return empresas.get(id);
        }
    }
}
