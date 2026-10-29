package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.security;

import java.nio.file.*;
import java.util.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import br.com.etecalbertoferes.CaixaDeSugestoes.domain.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigurationTest {
    @TempDir Path temp;
    @Test void missingSecretCreatesAndReusesPrivateKeyFile() throws Exception {
        Path file = temp.resolve("private/jwt.key");
        var env = new MockEnvironment().withProperty("app.jwt.key-file", file.toString());
        var config = new JwtConfiguration();
        byte[] first = config.jwtSigningKey(env).getEncoded();
        assertThat(first).hasSize(32);
        assertThat(config.jwtSigningKey(env).getEncoded()).isEqualTo(first);
        assertThat(Base64.getDecoder().decode(Files.readString(file))).isEqualTo(first);
        if (file.getFileSystem().supportedFileAttributeViews().contains("posix"))
            assertThat(Files.getPosixFilePermissions(file)).isEqualTo(java.nio.file.attribute.PosixFilePermissions.fromString("rw-------"));
    }
    @Test void invalidOrWeakExplicitKeysFailClosed() {
        var config = new JwtConfiguration();
        for (String secret : new String[]{"not base64!", "c2hvcnQ="})
            assertThatThrownBy(() -> config.jwtSigningKey(new MockEnvironment().withProperty("app.jwt.secret-base64", secret)))
                .isInstanceOf(IllegalStateException.class);
    }
    @Test void invalidExistingKeyIsNotOverwritten() throws Exception {
        Path file = temp.resolve("jwt.key"); Files.writeString(file, "broken key");
        assertThatThrownBy(() -> new JwtConfiguration().jwtSigningKey(new MockEnvironment().withProperty("app.jwt.key-file", file.toString())))
            .isInstanceOf(IllegalStateException.class);
        assertThat(Files.readString(file)).isEqualTo("broken key");
    }
    @Test void explicitKeyDoesNotCreateLocalFile() {
        Path file = temp.resolve("jwt.key");
        var env = new MockEnvironment().withProperty("app.jwt.key-file", file.toString())
            .withProperty("app.jwt.secret-base64", Base64.getEncoder().encodeToString(new byte[32]));
        assertThat(new JwtConfiguration().jwtSigningKey(env).getEncoded()).hasSize(32);
        assertThat(file).doesNotExist();
    }
    @Test void userDetailsComeFromDatabaseAndHonorActiveFlag() {
        AdminAccountRepository accounts = mock(AdminAccountRepository.class);
        when(accounts.findByUsername("missing")).thenReturn(java.util.Optional.empty());
        when(accounts.findByUsername("disabled")).thenReturn(java.util.Optional.of(new AdminAccount(1L,"disabled","hash",false)));
        var users = new AdminConfiguration().adminUsers(accounts);
        assertThatThrownBy(() -> users.loadUserByUsername("missing")).isInstanceOf(UsernameNotFoundException.class);
        assertThat(users.loadUserByUsername("disabled").isEnabled()).isFalse();
    }
}
