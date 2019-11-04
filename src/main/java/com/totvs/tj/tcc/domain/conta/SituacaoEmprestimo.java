package com.totvs.tj.tcc.domain.conta;

public enum SituacaoEmprestimo {

    PENDENTE {
        @Override
        public SituacaoEmprestimo nextState(Emprestimo emprestimo) {
           
            if( emprestimo.getValor() > emprestimo.getEmpresa().getContaLimite())
                return SEM_LIMITE_DISPONIVEL;
            
            if( emprestimo.getValor() > emprestimo.getValorMaximoSemAprovacaoGerente())
                return AGUARDANDO_APROVACAO;
            
            if( emprestimo.getEmpresa().isSupensa())
                return REPROVADO;
            
            return LIBERADO;
            
        }
    },
    
    AGUARDANDO_APROVACAO {
        @Override
        public SituacaoEmprestimo nextState(Emprestimo emprestimo) {
            return LIBERADO;
        }
    },
    
    LIBERADO {
        @Override
        public SituacaoEmprestimo nextState(Emprestimo emprestimo) {
            return QUITADO;
        }
    }, 
    
    SEM_LIMITE_DISPONIVEL {
        @Override
        public SituacaoEmprestimo nextState(Emprestimo emprestimo) {
            return this;
        }
    }, 
    
    REPROVADO {
        @Override
        public SituacaoEmprestimo nextState(Emprestimo emprestimo) {
            return this;
        }
    },
    
    QUITADO {
        @Override
        public SituacaoEmprestimo nextState(Emprestimo emprestimo) {
            return this;
        }
    };     

    public abstract SituacaoEmprestimo nextState(Emprestimo emprestimo);

}
