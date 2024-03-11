package br.com.helpit.empresa.exception;

import br.com.helpit.core.GenericoNaoEncontradoException;

import static java.lang.String.*;

public class EmpresaNaoEncontradaException extends GenericoNaoEncontradoException {

    public EmpresaNaoEncontradaException(Long empresaId) {
        super(format("Não foi possível localizar uma empresa com id %d.", empresaId));
    }
}
