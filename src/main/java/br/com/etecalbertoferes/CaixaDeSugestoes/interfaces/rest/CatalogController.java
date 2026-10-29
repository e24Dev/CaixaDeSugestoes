package br.com.etecalbertoferes.CaixaDeSugestoes.interfaces.rest;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import br.com.etecalbertoferes.CaixaDeSugestoes.application.CatalogService;
import br.com.etecalbertoferes.CaixaDeSugestoes.domain.*;
@RestController
@RequestMapping("/api")
public class CatalogController {
    private final CatalogService service;
    public CatalogController(CatalogService service) { this.service = service; }
    @GetMapping("/courses") public List<Course> courses() { return service.courses(); }
    @GetMapping("/categories") public List<Category> categories() { return service.categories(); }
}
