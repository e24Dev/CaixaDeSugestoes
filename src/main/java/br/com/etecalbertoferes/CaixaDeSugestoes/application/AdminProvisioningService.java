package br.com.etecalbertoferes.CaixaDeSugestoes.application;

import java.nio.charset.StandardCharsets;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.etecalbertoferes.CaixaDeSugestoes.domain.AdminAccountRepository;
@Service
public class AdminProvisioningService {
    private final AdminAccountRepository accounts;
    private final PasswordEncoder encoder;
    public AdminProvisioningService(AdminAccountRepository accounts, PasswordEncoder encoder) {
        this.accounts = accounts; this.encoder = encoder;
    }
    @Transactional
    public void provision(String username, String password) {
        if (username == null || username.isBlank() || username.length() > 100 || !username.equals(username.strip()))
            throw new IllegalArgumentException("Use um usuário de até 100 caracteres, sem espaços nas extremidades");
        if (password == null || password.length() < 12 || password.getBytes(StandardCharsets.UTF_8).length > 72)
            throw new IllegalArgumentException("A senha deve ter ao menos 12 caracteres e no máximo 72 bytes UTF-8");
        if (!accounts.createInitial(username, encoder.encode(password)))
            throw new IllegalStateException("Já existe administrador; o provisionamento não altera contas existentes");
    }
}
