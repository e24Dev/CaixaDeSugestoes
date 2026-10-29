package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
interface CourseJpaRepository extends JpaRepository<CourseEntity, Long> {
    List<CourseEntity> findAllByOrderByNameAsc();
}
