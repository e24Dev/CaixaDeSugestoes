package br.com.etecalbertoferes.CaixaDeSugestoes.domain;

import java.util.List;
import java.util.Optional;
public interface SuggestionRepository {
    Suggestion save(Suggestion suggestion);
    Optional<Suggestion> findById(Long id);
    List<Suggestion> search(Long courseId, Long categoryId, String term);
    void delete(Long id);
}
