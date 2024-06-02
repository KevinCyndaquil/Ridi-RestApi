package ridi.web.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ridi.web.services.UsuarioRidiService;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class AppConfig {
    final JpaProperties hibernateProperties;
    final UsuarioRidiService usuariosService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuariosService);
        return provider;
    }
}
