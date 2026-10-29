package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import br.com.etecalbertoferes.CaixaDeSugestoes.domain.*;
@Repository
public class CoursePersistenceAdapter implements CourseRepository {
    private final CourseJpaRepository repository;
    public CoursePersistenceAdapter(CourseJpaRepository repository) { this.repository = repository; }
    public List<Course> findAll() { return repository.findAllByOrderByNameAsc().stream().map(e -> new Course(e.id, e.name)).toList(); }
    public Optional<Course> findById(Long id) { return repository.findById(id).map(e -> new Course(e.id, e.name)); }
}
