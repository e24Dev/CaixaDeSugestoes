package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;
import static org.assertj.core.api.Assertions.*;

class SecurityConfigurationTest {
    @Test void missingInvalidAndWeakSigningKeysFailClosed() {
        JwtConfiguration config = new JwtConfiguration();
        assertThatThrownBy(() -> config.jwtSigningKey(new MockEnvironment())).isInstanceOf(IllegalStateException.class);
        for (String secret : new String[]{"not base64!", "", "c2hvcnQ="})
            assertThatThrownBy(() -> config.jwtSigningKey(new MockEnvironment().withProperty("app.jwt.secret-base64", secret)))
                .isInstanceOf(IllegalStateException.class);
    }
    @Test void missingOrPlaintextAdminPasswordIsRejected() {
        AdminConfiguration config = new AdminConfiguration();
        assertThatThrownBy(() -> config.adminUsers(new MockEnvironment())).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> config.adminUsers(new MockEnvironment().withProperty("app.admin.username", "admin")
            .withProperty("app.admin.password-hash", "plaintext"))).isInstanceOf(IllegalStateException.class);
    }
}
