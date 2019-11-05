package com.totvs.tj.tcc.domain.conta;

public enum SituacaoEmprestimo {

    PENDENTE {
        @Override
        public SituacaoEmprestimo proximaSituacao(Emprestimo emprestimo) {

            if (emprestimo.getIsSemLimiteDisponivel())
                return SEM_LIMITE_DISPONIVEL;

            if (emprestimo.getIsAguardandoAprovacao())
                return AGUARDANDO_APROVACAO;

            if (emprestimo.getIsSupensa())
                return REPROVADO;

            return LIBERADO;

        }
    },

    AGUARDANDO_APROVACAO {
        @Override
        public SituacaoEmprestimo proximaSituacao(Emprestimo emprestimo) {
            return LIBERADO;
        }
    },

    LIBERADO {
        @Override
        public SituacaoEmprestimo proximaSituacao(Emprestimo emprestimo) {
            return QUITADO;
        }
    },

    SEM_LIMITE_DISPONIVEL {
        @Override
        public SituacaoEmprestimo proximaSituacao(Emprestimo emprestimo) {
            return this;
        }
    },

    REPROVADO {
        @Override
        public SituacaoEmprestimo proximaSituacao(Emprestimo emprestimo) {
            return this;
        }
    },

    QUITADO {
        @Override
        public SituacaoEmprestimo proximaSituacao(Emprestimo emprestimo) {
            return this;
        }
    };

    public abstract SituacaoEmprestimo proximaSituacao(Emprestimo emprestimo);

}
