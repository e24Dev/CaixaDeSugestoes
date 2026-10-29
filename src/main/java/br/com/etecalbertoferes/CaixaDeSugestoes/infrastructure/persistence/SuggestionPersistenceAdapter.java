package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import br.com.etecalbertoferes.CaixaDeSugestoes.domain.*;

@Repository
public class SuggestionPersistenceAdapter implements SuggestionRepository {
    private final SuggestionJpaRepository repository;
    private final EntityManager em;
    public SuggestionPersistenceAdapter(SuggestionJpaRepository repository, EntityManager em) {
        this.repository = repository; this.em = em;
    }
    public Suggestion save(Suggestion suggestion) {
        SuggestionEntity entity = suggestion.id() == null ? new SuggestionEntity() : repository.findById(suggestion.id()).orElseThrow(() -> new ResourceNotFoundException("Sugestão não encontrada"));
        entity.title = suggestion.title(); entity.content = suggestion.content(); entity.author = suggestion.author();
        entity.createdAt = suggestion.createdAt();
        entity.course = em.getReference(CourseEntity.class, suggestion.course().id());
        entity.category = em.getReference(CategoryEntity.class, suggestion.category().id());
        return map(repository.save(entity));
    }
    public Optional<Suggestion> findById(Long id) { return repository.findById(id).map(this::map); }
    public List<Suggestion> search(Long courseId, Long categoryId, String term) {
        Specification<SuggestionEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (courseId != null) predicates.add(cb.equal(root.get("course").get("id"), courseId));
            if (categoryId != null) predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            if (term != null) {
                String pattern = "%" + term.toLowerCase(Locale.ROOT).replace("!", "!!").replace("%", "!%").replace("_", "!_") + "%";
                predicates.add(cb.or(cb.like(cb.lower(root.get("title")), pattern, '!'), cb.like(cb.lower(root.get("content")), pattern, '!')));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
        return repository.findAll(spec, Sort.by(Sort.Order.desc("createdAt"), Sort.Order.desc("id"))).stream().map(this::map).toList();
    }
    public void delete(Long id) { repository.deleteById(id); }
    private Suggestion map(SuggestionEntity e) {
        return new Suggestion(e.id, e.title, e.content, e.author, e.createdAt,
            new Course(e.course.getId(), e.course.getName()), new Category(e.category.getId(), e.category.getName()));
    }
}
