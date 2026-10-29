package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import br.com.etecalbertoferes.CaixaDeSugestoes.domain.*;
@Repository
public class CategoryPersistenceAdapter implements CategoryRepository {
    private final CategoryJpaRepository repository;
    public CategoryPersistenceAdapter(CategoryJpaRepository repository) { this.repository = repository; }
    public List<Category> findAll() { return repository.findAllByOrderByNameAsc().stream().map(e -> new Category(e.id, e.name)).toList(); }
    public Optional<Category> findById(Long id) { return repository.findById(id).map(e -> new Category(e.id, e.name)); }
}
