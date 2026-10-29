package br.com.etecalbertoferes.CaixaDeSugestoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CaixaDeSugestoesApplication {
    public static void main(String[] args) {
        if (args.length == 1 && "--setup-admin".equals(args[0])) {
            br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.security.AdminSetupCommand.run();
            return;
        }
        if (args.length == 1 && "--generate-admin-password-hash".equals(args[0])) {
            br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.security.AdminPasswordHash.generate();
            return;
        }
        if (args.length == 1 && "--generate-jwt-secret".equals(args[0])) {
            byte[] key = new byte[32];
            new java.security.SecureRandom().nextBytes(key);
            System.out.println(java.util.Base64.getEncoder().encodeToString(key));
            return;
        }
        SpringApplication.run(CaixaDeSugestoesApplication.class, args);
    }
}
