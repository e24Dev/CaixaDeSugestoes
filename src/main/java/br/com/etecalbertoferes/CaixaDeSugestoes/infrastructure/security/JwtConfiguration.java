package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.security;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import java.nio.file.Path;
import br.com.etecalbertoferes.CaixaDeSugestoes.domain.AdminAccountRepository;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import br.com.etecalbertoferes.CaixaDeSugestoes.application.AdminAuthenticationService;

@Configuration
public class JwtConfiguration {
    @Bean
    SecretKey jwtSigningKey(Environment env) {
        byte[] decoded;
        try {
            String configured = env.getProperty("app.jwt.secret-base64", "");
            String secret = configured.isBlank()
                ? JwtKeyStore.loadOrCreate(Path.of(env.getProperty("app.jwt.key-file", ".local/jwt-signing.key")))
                : configured;
            decoded = Base64.getDecoder().decode(secret);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("Configure JWT_SECRET_BASE64 com uma chave Base64 válida");
        }
        if (decoded.length < 32)
            throw new IllegalStateException("JWT_SECRET_BASE64 deve representar pelo menos 32 bytes aleatórios");
        return new SecretKeySpec(decoded, "HmacSHA256");
    }

    @Bean
    JwtEncoder jwtEncoder(SecretKey key) {
        return NimbusJwtEncoder.withSecretKey(key).algorithm(MacAlgorithm.HS256).build();
    }

    @Bean
    JwtDecoder jwtDecoder(SecretKey key, AdminAccountRepository accounts) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
        OAuth2TokenValidator<Jwt> requiredTimes = jwt -> {
            if (jwt.getExpiresAt() == null || jwt.getIssuedAt() == null
                    || jwt.getIssuedAt().isAfter(Instant.now().plusSeconds(30)))
                return OAuth2TokenValidatorResult
                        .failure(new OAuth2Error("invalid_token", "Claims temporais inválidas", null));
            return OAuth2TokenValidatorResult.success();
        };
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                new JwtTimestampValidator(Duration.ZERO),
                new JwtIssuerValidator(AdminAuthenticationService.ISSUER),
                new JwtClaimValidator<List<String>>(JwtClaimNames.AUD,
                        aud -> aud != null && aud.contains(AdminAuthenticationService.AUDIENCE)),
                new JwtClaimValidator<String>(JwtClaimNames.SUB, subject -> subject != null &&
                    accounts.findByUsername(subject).filter(account -> account.active()).isPresent()),
                requiredTimes));
        return decoder;
    }
}
