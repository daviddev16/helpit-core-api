package br.com.helpit.global.annotation;

import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Essa anotação apenas é usada para indicar quando algum método, internamente
 * utiliza o facade de autenticação do {@link  SecurityContextHolder#getContext()}
 * através de {@link br.com.helpit.core.AuthenticatedService} para realizar validação
 * de autenticação de usuário.
 *
 * @see br.com.helpit.core.AuthenticatedService
 *
 **/
@Retention(RetentionPolicy.CLASS)
public @interface WithAutheticationFacade { }
