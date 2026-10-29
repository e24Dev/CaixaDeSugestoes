package br.com.etecalbertoferes.CaixaDeSugestoes.interfaces.rest;

import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;
import br.com.etecalbertoferes.CaixaDeSugestoes.domain.*;
public record SuggestionResponse(Long id, String title, String content, @Schema(description = "Nome normalizado ou Anônimo") String author, @Schema(description = "Data de criação UTC, preservada no PUT", example = "2026-10-08T12:00:00Z") Instant createdAt, Course course, Category category) {
    public static SuggestionResponse from(Suggestion suggestion) {
        return new SuggestionResponse(suggestion.id(), suggestion.title(), suggestion.content(),
            suggestion.author() == null ? "Anônimo" : suggestion.author(), suggestion.createdAt(), suggestion.course(), suggestion.category());
    }
}
