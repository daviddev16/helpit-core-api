package br.com.helpit.usuario.exception;

import br.com.helpit.core.ServiceException;

public class CodigoConfirmacaoNaoEncontradoException extends ServiceException {

    public CodigoConfirmacaoNaoEncontradoException() {
        super("O código de confirmação informado não é válido.");
    }
}
