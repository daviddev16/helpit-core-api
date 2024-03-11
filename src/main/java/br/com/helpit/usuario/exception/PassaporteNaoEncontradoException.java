package br.com.helpit.usuario.exception;

import br.com.helpit.core.ServiceException;

import static java.lang.String.*;

public class PassaporteNaoEncontradoException extends ServiceException {

    public PassaporteNaoEncontradoException(String passaporte) {
        super(format("Passaporte '%s' não foi encontrado.", passaporte));
    }

}
