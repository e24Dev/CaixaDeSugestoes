package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByOrderByNameAsc();
}
