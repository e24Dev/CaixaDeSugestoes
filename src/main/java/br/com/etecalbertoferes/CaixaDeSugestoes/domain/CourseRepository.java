package br.com.etecalbertoferes.CaixaDeSugestoes.domain;

import java.util.List;
import java.util.Optional;
public interface CourseRepository {
    List<Course> findAll();
    Optional<Course> findById(Long id);
}
