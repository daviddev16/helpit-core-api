package br.com.helpit.artigo.exception;

import br.com.helpit.core.GenericoNaoEncontradoException;

import static java.lang.String.format;

public class ArtigoNaoEncontradoException extends GenericoNaoEncontradoException {

    public ArtigoNaoEncontradoException(Long idArtigo) {
        super(format("Não foi possível localizar um artigo com id %d.", idArtigo));
    }
}
