package com.totvs.tj.tcc.app;

import org.springframework.stereotype.Service;

import com.totvs.tj.tcc.domain.conta.Emprestimo;
import com.totvs.tj.tcc.domain.conta.EmprestimoId;
import com.totvs.tj.tcc.domain.conta.EmprestimoRepository;

import lombok.Builder;

@Service
@Builder
public class EmprestimoApplicationService {

    private EmprestimoRepository repository;

    public EmprestimoApplicationService(EmprestimoRepository repository) {
        this.repository = repository;
    }

    public EmprestimoId handle(SolicitarEmprestimoCommand cmd) {
        Emprestimo emprestimo = Emprestimo.builder()
                .empresa(cmd.getEmpresa())
                .id(cmd.getIdEmprestimo())
                .valor(cmd.getValor())
                .situacao(cmd.getSituacao())
                .build();

        repository.save(emprestimo);

        return emprestimo.getId();
    }

    public EmprestimoId handle(QuitarEmprestimoCommand cmd) {
        Emprestimo emprestimo = cmd.getEmprestimo();

        emprestimo.quitarEmprestimo();

        repository.save(emprestimo);

        return emprestimo.getId();
    }

    public EmprestimoId handle(AprovarEmprestimoCommand cmd) {
        Emprestimo emprestimo = cmd.getEmprestimo();

        emprestimo.aprovarEmprestimo();

        repository.save(emprestimo);

        return emprestimo.getId();
    }

}
