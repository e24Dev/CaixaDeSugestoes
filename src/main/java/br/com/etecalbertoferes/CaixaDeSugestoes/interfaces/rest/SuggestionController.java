package br.com.etecalbertoferes.CaixaDeSugestoes.interfaces.rest;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuggestionController {
    @GetMapping("/api/suggestions")
    public List<Object> list() { return List.of(); }
}
