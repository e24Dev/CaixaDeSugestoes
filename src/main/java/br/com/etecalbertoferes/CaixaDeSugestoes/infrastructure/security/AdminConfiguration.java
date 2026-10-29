package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.security;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AdminConfiguration {
    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(12); }
    @Bean UserDetailsService adminUsers(Environment env) {
        String username = env.getRequiredProperty("app.admin.username");
        String hash = env.getRequiredProperty("app.admin.password-hash");
        if (username.isBlank() || !hash.matches("\\$2[aby]\\$\\d{2}\\$[./A-Za-z0-9]{53}")) {
            throw new IllegalStateException("Configure ADMIN_USERNAME e ADMIN_PASSWORD_HASH com um hash BCrypt válido");
        }
        return new InMemoryUserDetailsManager(User.withUsername(username).password(hash).roles("ADMIN").build());
    }
    @Bean AuthenticationManager authenticationManager(UserDetailsService users, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(users);
        provider.setPasswordEncoder(encoder);
        return new ProviderManager(provider);
    }
}
