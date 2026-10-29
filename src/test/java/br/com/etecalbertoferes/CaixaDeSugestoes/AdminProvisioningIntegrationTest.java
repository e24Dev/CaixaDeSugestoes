package br.com.etecalbertoferes.CaixaDeSugestoes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;

@Transactional
class AdminProvisioningIntegrationTest extends ContextTest {
    @Autowired PasswordEncoder encoder;
    @Test void firstAccountStoresHashAndRefusesReplacement() {
        jdbc.update("delete from administrators");
        provisioning.provision("first-admin", ADMIN_PASSWORD);
        var account = accounts.findByUsername("first-admin").orElseThrow();
        assertThat(account.active()).isTrue();
        assertThat(account.passwordHash()).isNotEqualTo(ADMIN_PASSWORD);
        assertThat(encoder.matches(ADMIN_PASSWORD, account.passwordHash())).isTrue();
        assertThatThrownBy(() -> provisioning.provision("another-admin", "Another-Password-2026!"))
            .isInstanceOf(IllegalStateException.class).hasMessageContaining("Já existe administrador");
        assertThat(jdbc.queryForObject("select count(*) from administrators", Long.class)).isEqualTo(1L);
        assertThat(accounts.findByUsername("first-admin").orElseThrow().passwordHash()).isEqualTo(account.passwordHash());
    }
    @Test void invalidInputDoesNotCreateAccounts() {
        jdbc.update("delete from administrators");
        for (String name : new String[]{"", "  ", " padded ", "x".repeat(101)})
            assertThatThrownBy(() -> provisioning.provision(name, ADMIN_PASSWORD)).isInstanceOf(IllegalArgumentException.class);
        for (String password : new String[]{"short", "ç".repeat(50)})
            assertThatThrownBy(() -> provisioning.provision("admin", password)).isInstanceOf(IllegalArgumentException.class);
        assertThat(jdbc.queryForObject("select count(*) from administrators", Long.class)).isZero();
    }
}
