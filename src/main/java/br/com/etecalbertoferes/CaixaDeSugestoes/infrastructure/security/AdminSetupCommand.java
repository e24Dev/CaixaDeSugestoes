package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.security;

import java.io.Console;
import java.util.Arrays;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import br.com.etecalbertoferes.CaixaDeSugestoes.CaixaDeSugestoesApplication;
import br.com.etecalbertoferes.CaixaDeSugestoes.application.AdminProvisioningService;
public final class AdminSetupCommand {
    private AdminSetupCommand() {}
    public static void run() {
        Console console = System.console();
        if (console == null) throw new IllegalStateException("Execute o JAR em terminal interativo com --setup-admin");
        String username = console.readLine("Usuário do administrador: ");
        char[] password = console.readPassword("Senha (mínimo 12 caracteres): ");
        char[] confirmation = console.readPassword("Confirme a senha: ");
        try {
            if (password == null || confirmation == null || !Arrays.equals(password, confirmation))
                throw new IllegalArgumentException("As senhas não conferem ou a leitura foi cancelada");
            try (var context = new SpringApplicationBuilder(CaixaDeSugestoesApplication.class)
                    .web(WebApplicationType.NONE).run()) {
                context.getBean(AdminProvisioningService.class).provision(username, new String(password));
                console.printf("Administrador cadastrado no banco. Inicie a API e faça login com a senha escolhida.%n");
            }
        } finally {
            if (password != null) Arrays.fill(password, '\0');
            if (confirmation != null) Arrays.fill(confirmation, '\0');
        }
    }
}
