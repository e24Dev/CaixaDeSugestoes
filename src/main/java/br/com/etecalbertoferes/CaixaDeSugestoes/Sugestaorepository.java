package br.com.etecalbertoferes.CaixaDeSugestoes;

import br.com.etecalbertoferes.CaixaDeSugestoes.entities.SugestaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Sugestaorepository extends JpaRepository<SugestaoEntity, Long> {
}
