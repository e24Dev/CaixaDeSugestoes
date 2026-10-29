package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
interface AdminAccountJpaRepository extends JpaRepository<AdminAccountEntity, Long> {
    Optional<AdminAccountEntity> findByUsername(String username);
}
