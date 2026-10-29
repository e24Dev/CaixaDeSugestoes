package br.com.etecalbertoferes.CaixaDeSugestoes.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import br.com.etecalbertoferes.CaixaDeSugestoes.application.CatalogService;
import br.com.etecalbertoferes.CaixaDeSugestoes.domain.*;

@Tag(name = "Catálogos", description = "Cursos e categorias didáticos, ordenados por nome; somente consulta")
@RestController
@RequestMapping("/api")
public class CatalogController {
    private final CatalogService service;

    public CatalogController(CatalogService service) {
        this.service = service;
    }

    @Operation(summary = "Listar cursos", description = "Array de id/name ordenado por name crescente")
    @GetMapping("/courses")
    public List<Course> courses() {
        return service.courses();
    }

    @Operation(summary = "Listar categorias", description = "Array de id/name ordenado por name crescente")
    @GetMapping("/categories")
    public List<Category> categories() {
        return service.categories();
    }
}
