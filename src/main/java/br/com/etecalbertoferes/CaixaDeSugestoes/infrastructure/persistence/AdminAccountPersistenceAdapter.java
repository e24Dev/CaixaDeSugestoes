package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.persistence;

import java.util.Optional;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.etecalbertoferes.CaixaDeSugestoes.domain.*;
@Repository
@Transactional(readOnly = true)
public class AdminAccountPersistenceAdapter implements AdminAccountRepository {
    private final AdminAccountJpaRepository repository;
    private final EntityManager em;
    public AdminAccountPersistenceAdapter(AdminAccountJpaRepository repository, EntityManager em) {
        this.repository = repository; this.em = em;
    }
    public Optional<AdminAccount> findByUsername(String username) {
        return repository.findByUsername(username).map(e -> new AdminAccount(e.id, e.username, e.passwordHash, e.active));
    }
    @Transactional
    public boolean createInitial(String username, String passwordHash) {
        // Serializa apenas o provisionamento; duas execuções não criam duas contas iniciais.
        em.createNativeQuery("LOCK TABLE administrators IN EXCLUSIVE MODE").executeUpdate();
        if (repository.count() != 0) return false;
        AdminAccountEntity entity = new AdminAccountEntity();
        entity.username = username; entity.passwordHash = passwordHash; entity.active = true;
        repository.saveAndFlush(entity);
        return true;
    }
}
