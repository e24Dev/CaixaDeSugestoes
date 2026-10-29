package br.com.etecalbertoferes.CaixaDeSugestoes.interfaces.rest;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.etecalbertoferes.CaixaDeSugestoes.application.SuggestionService;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {
    private final SuggestionService service;
    public SuggestionController(SuggestionService service) { this.service = service; }
    @GetMapping
    public List<SuggestionResponse> list(@RequestParam(required = false) Long courseId,
            @RequestParam(required = false) Long categoryId, @RequestParam(required = false) String q) {
        return service.list(courseId, categoryId, q).stream().map(SuggestionResponse::from).toList();
    }
    @GetMapping("/{id}")
    public SuggestionResponse get(@PathVariable Long id) { return SuggestionResponse.from(service.get(id)); }
    @PostMapping
    public ResponseEntity<SuggestionResponse> create(@RequestBody SuggestionRequest request) {
        SuggestionResponse response = SuggestionResponse.from(service.create(request.command()));
        return ResponseEntity.created(URI.create("/api/suggestions/" + response.id())).body(response);
    }
    @PutMapping("/{id}")
    public SuggestionResponse update(@PathVariable Long id, @RequestBody SuggestionRequest request) {
        return SuggestionResponse.from(service.update(id, request.command()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
