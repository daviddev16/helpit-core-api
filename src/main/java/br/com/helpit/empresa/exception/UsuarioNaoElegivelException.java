package br.com.helpit.empresa.exception;

import br.com.helpit.core.ServiceException;

public class UsuarioNaoElegivelException extends ServiceException {

    public UsuarioNaoElegivelException(String message) {
        super(message);
    }

}
