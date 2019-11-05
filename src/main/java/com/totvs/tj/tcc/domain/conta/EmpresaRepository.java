package com.totvs.tj.tcc.domain.conta;

public interface EmpresaRepository {

    void save(Empresa empresa);

    void update(Empresa empresa);

    Empresa getOne(EmpresaId id);

}
