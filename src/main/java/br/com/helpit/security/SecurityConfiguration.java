package br.com.helpit.security;

import br.com.helpit.usuario.enums.CargoInterno;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UsuarioAuthenticationProvider usuarioAuthenticationProvider;

    public SecurityConfiguration(UsuarioAuthenticationProvider usuarioAuthenticationProvider) {
        this.usuarioAuthenticationProvider = usuarioAuthenticationProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer ->
                {
                    customizer
                            .requestMatchers(HttpMethod.POST, "/v1/auth/**")
                            .permitAll();

                    customizer
                            .anyRequest()
                            .hasAnyRole(
                                    CargoInterno.USUARIO.name(),
                                    CargoInterno.ADMINISTRADOR.name());
                })
                .authenticationProvider(usuarioAuthenticationProvider)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

}
