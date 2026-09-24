package br.com.etecalbertoferes.CaixaDeSugestoes.resources;

import br.com.etecalbertoferes.CaixaDeSugestoes.Sugestaorepository;
import br.com.etecalbertoferes.CaixaDeSugestoes.entities.SugestaoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sugestao")
public class SugestaoResource {

    private final Sugestaorepository repository;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody SugestaoEntity sugestao) {
        System.out.println("Recebi sua requisição de criação de sugestão");
        SugestaoEntity saved = repository.save(sugestao);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        System.out.println("Requisicao de consulta recebida com sucesso para o ID: " + id);
        Optional<SugestaoEntity> sugestao = repository.findById(id);
        if (sugestao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SugestaoEntity found = sugestao.get();
        return ResponseEntity.ok(found);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        System.out.println("Requisicao de exclusao recebida com sucesso para o ID: " + id);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<?> atualizar() {
        System.out.println("Requisicao de atualizacao recebida com sucesso");
        return ResponseEntity.ok().build();
    }
}
