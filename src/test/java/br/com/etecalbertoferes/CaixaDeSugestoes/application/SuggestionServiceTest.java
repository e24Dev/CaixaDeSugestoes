package br.com.etecalbertoferes.CaixaDeSugestoes.application;

import br.com.etecalbertoferes.CaixaDeSugestoes.domain.*;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuggestionServiceTest {
    @Mock SuggestionRepository suggestions;
    @Mock CourseRepository courses;
    @Mock CategoryRepository categories;
    @InjectMocks SuggestionService service;
    final Course course = new Course(10L, "Curso");
    final Category category = new Category(20L, "Sugestão");
    SuggestionCommand command(String author) { return new SuggestionCommand(" título ", " conteúdo ", author, 10L, 20L); }
    void catalogs() {
        when(courses.findById(10L)).thenReturn(Optional.of(course));
        when(categories.findById(20L)).thenReturn(Optional.of(category));
        when(suggestions.save(any())).thenAnswer(i -> i.getArgument(0));
    }
    @Test void createNormalizesAuthorAndAssignsTime() {
        catalogs();
        Instant before = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        Suggestion created = service.create(command("  Pessoa  "));
        assertThat(created.author()).isEqualTo("Pessoa");
        assertThat(created.title()).isEqualTo(" título ");
        assertThat(created.content()).isEqualTo(" conteúdo ");
        assertThat(created.id()).isNull();
        assertThat(created.createdAt()).isBetween(before, Instant.now());
        assertThat(service.create(command(null)).author()).isNull();
        assertThat(service.create(command(" \t ")).author()).isNull();
    }
    @Test void updatePreservesIdentityAndTimestampAndRemovesAuthor() {
        catalogs();
        Instant time = Instant.parse("2026-01-01T00:00:00Z");
        when(suggestions.findById(7L)).thenReturn(Optional.of(new Suggestion(7L, "old", "old", "Nome", time, course, category)));
        Suggestion updated = service.update(7L, command(null));
        assertThat(updated.id()).isEqualTo(7L);
        assertThat(updated.createdAt()).isEqualTo(time);
        assertThat(updated.author()).isNull();
    }
    @Test void searchNormalizesTermAndPreservesFilters() {
        service.list(10L, 20L, "  100%_!  ");
        verify(suggestions).search(10L, 20L, "100%_!");
        service.list(null, null, "  ");
        verify(suggestions).search(null, null, null);
    }
    @Test void missingSuggestionCannotUpdateOrDelete() {
        when(suggestions.findById(7L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(7L, command(null))).isInstanceOf(ResourceNotFoundException.class);
        assertThatThrownBy(() -> service.delete(7L)).isInstanceOf(ResourceNotFoundException.class);
        verify(suggestions, never()).save(any());
        verify(suggestions, never()).delete(any());
    }
    @Test void missingCatalogDoesNotSave() {
        when(courses.findById(10L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(command(null))).isInstanceOf(ResourceNotFoundException.class).hasMessage("Curso não encontrado");
        when(courses.findById(10L)).thenReturn(Optional.of(course));
        when(categories.findById(20L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(command(null))).isInstanceOf(ResourceNotFoundException.class).hasMessage("Categoria não encontrada");
        verify(suggestions, never()).save(any());
    }
    @Test void deleteChecksExistence() {
        when(suggestions.findById(7L)).thenReturn(Optional.of(new Suggestion(7L, "t", "c", null, Instant.now(), course, category)));
        service.delete(7L);
        verify(suggestions).delete(7L);
    }
}
