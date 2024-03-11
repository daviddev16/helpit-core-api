package br.com.helpit.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfiguration {

    private final PasswordEncoder passwordEncoder;

    public PasswordConfiguration() {
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    public PasswordEncoder getEnconder() {
        return passwordEncoder;
    }
}
