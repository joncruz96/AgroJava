package com.totvs.tj.tcc.domain.conta;

public interface EmprestimoRepository {

    void save(Emprestimo emprestimo);
    
    Emprestimo getOne(EmprestimoId id);
    
}
