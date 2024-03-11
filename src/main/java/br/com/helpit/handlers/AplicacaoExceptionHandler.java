package br.com.helpit.handlers;

import br.com.helpit.core.ApiErrorDetails;
import br.com.helpit.core.ServiceException;
import br.com.helpit.empresa.exception.UsuarioNaoElegivelException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class AplicacaoExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceException.class)
    public ApiErrorDetails handleServiceException(ServiceException serviceException) {
        return new ApiErrorDetails(serviceException.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UsuarioNaoElegivelException.class)
    public ApiErrorDetails handleUsuarioNaoElegivelException(UsuarioNaoElegivelException usuarioNaoElegivelException) {
        return handleServiceException(usuarioNaoElegivelException);
    }

}
