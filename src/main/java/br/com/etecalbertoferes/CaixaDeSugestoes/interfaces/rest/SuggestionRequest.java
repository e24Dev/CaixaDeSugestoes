package br.com.etecalbertoferes.CaixaDeSugestoes.interfaces.rest;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import br.com.etecalbertoferes.CaixaDeSugestoes.application.SuggestionCommand;
public record SuggestionRequest(
    @NotBlank(message = "Título é obrigatório") @Schema(description = "Título obrigatório; texto preservado", example = "Mais tomadas na biblioteca") String title,
    @NotBlank(message = "Conteúdo é obrigatório") @Schema(description = "Conteúdo obrigatório; texto preservado", example = "Instalar tomadas nas mesas de estudo.") String content,
    @Schema(description = "Nome opcional; espaços externos removidos. Ausente, nulo ou em branco torna a sugestão anônima", example = "Pessoa Exemplo") String author,
    @NotNull(message = "Curso é obrigatório") @Positive(message = "Curso deve ser positivo") @Schema(description = "ID de curso existente", example = "1") Long courseId,
    @NotNull(message = "Categoria é obrigatória") @Positive(message = "Categoria deve ser positiva") @Schema(description = "ID de categoria existente", example = "1") Long categoryId) {
    public SuggestionCommand command() { return new SuggestionCommand(title, content, author, courseId, categoryId); }
}
