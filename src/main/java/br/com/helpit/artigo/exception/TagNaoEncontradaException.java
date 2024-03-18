package br.com.helpit.artigo.exception;

import br.com.helpit.core.GenericoNaoEncontradoException;

import static java.lang.String.format;

public class TagNaoEncontradaException extends GenericoNaoEncontradoException {

    public TagNaoEncontradaException(String nomeTag, Long idEmpresa) {
        super(format("Não foi possível localizar uma tag com o " +
                "nome \"%s\" para a empresa \"%d\".", nomeTag, idEmpresa));
    }
}
