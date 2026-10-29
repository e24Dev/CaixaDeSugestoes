package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.security;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class AdminPasswordHash {
    private AdminPasswordHash() {
    }

    public static void generate() {
        Console console = System.console();
        if (console == null)
            throw new IllegalStateException(
                    "Execute em terminal interativo: java -jar ... --generate-admin-password-hash");
        char[] password = console.readPassword("Senha administrativa (mínimo 12 caracteres): ");
        if (password == null)
            throw new IllegalStateException("Leitura cancelada");
        try {
            String value = new String(password);
            if (password.length < 12 || value.getBytes(StandardCharsets.UTF_8).length > 72)
                throw new IllegalArgumentException("Use ao menos 12 caracteres e no máximo 72 bytes UTF-8");
            console.printf("%s%n", new BCryptPasswordEncoder(12).encode(value));
        } finally {
            Arrays.fill(password, '\0');
        }
    }
}
