package br.com.helpit.security;

import br.com.helpit.usuario.Usuario;
import br.com.helpit.usuario.service.UsuarioService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioAuthenticationProvider implements AuthenticationProvider {

    private final PasswordConfiguration passwordConfiguration;
    private final UsuarioService usuarioService;

    public UsuarioAuthenticationProvider(PasswordConfiguration passwordConfiguration,
                                         UsuarioService usuarioService)
    {
        this.passwordConfiguration = passwordConfiguration;
        this.usuarioService = usuarioService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final String passaporte = authentication.getName();
        final String senha  = (String) authentication.getCredentials();

        Usuario usuario = usuarioService.obterUsuarioPorLoginOuEmail(passaporte);

        if (passwordConfiguration.getEnconder().matches(senha, usuario.getSenha())) {
            return new UsernamePasswordAuthenticationToken(passaporte, null, List.of(new SimpleGrantedAuthority("USUARIO")));
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
