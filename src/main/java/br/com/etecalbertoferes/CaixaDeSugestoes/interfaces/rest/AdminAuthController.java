package br.com.etecalbertoferes.CaixaDeSugestoes.interfaces.rest;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.*;
import br.com.etecalbertoferes.CaixaDeSugestoes.application.AdminAuthenticationService;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Administração", description = "Login exclusivo do administrador; sem cadastro público")
public class AdminAuthController {
    private final AdminAuthenticationService service;

    public AdminAuthController(AdminAuthenticationService service) {
        this.service = service;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Autenticar administrador e emitir JWT", description = "Envie username e password. accessToken deve ser apresentado como Authorization: Bearer <token>. Expiração em segundos; não há refresh nem revogação individual nesta etapa.")
    @ApiResponse(responseCode = "200", description = "Token emitido")
    @ApiResponse(responseCode = "400", description = "Corpo inválido", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<AdminAuthenticationService.AccessToken> login(@Valid @RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok().cacheControl(CacheControl.noStore()).header(HttpHeaders.PRAGMA, "no-cache")
                .body(service.login(request.username(), request.password()));
    }
}
