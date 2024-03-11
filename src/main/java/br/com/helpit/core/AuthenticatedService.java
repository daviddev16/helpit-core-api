package br.com.helpit.core;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface AuthenticatedService {

    default Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
