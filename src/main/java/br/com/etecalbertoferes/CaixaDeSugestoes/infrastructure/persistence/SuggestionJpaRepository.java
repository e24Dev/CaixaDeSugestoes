package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;

interface SuggestionJpaRepository extends JpaRepository<SuggestionEntity, Long>, JpaSpecificationExecutor<SuggestionEntity> {
    @Override
    @EntityGraph(attributePaths = {"course", "category"})
    Optional<SuggestionEntity> findById(Long id);
    @Override
    @EntityGraph(attributePaths = {"course", "category"})
    List<SuggestionEntity> findAll(org.springframework.data.jpa.domain.Specification<SuggestionEntity> spec, org.springframework.data.domain.Sort sort);
}
