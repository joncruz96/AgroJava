package com.totvs.tj.tcc.domain.conta;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor(access = PRIVATE)
public class Responsavel {
    
    private ResponsavelId id;
    
    private final TipoPessoa tipoPessoa = TipoPessoa.FISICA ;
    
}
