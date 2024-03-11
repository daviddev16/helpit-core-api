package br.com.helpit.core;

public abstract class GenericoNaoEncontradoException extends ServiceException {

    public GenericoNaoEncontradoException(String message) {
        super(message);
    }
}
