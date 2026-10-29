package br.com.etecalbertoferes.CaixaDeSugestoes.interfaces.rest;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import br.com.etecalbertoferes.CaixaDeSugestoes.domain.ResourceNotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> fields.putIfAbsent(e.getField(), e.getDefaultMessage()));
        return respond(status, "Dados inválidos", fields, headers, request);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getParameterValidationResults().forEach(result -> {
            String name = result.getMethodParameter().getParameterName();
            result.getResolvableErrors()
                    .forEach(error -> fields.putIfAbsent(name == null ? "id" : name, error.getDefaultMessage()));
        });
        return respond(status, "Dados inválidos", fields, headers, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        String message = switch (status.value()) {
            case 400 -> "Requisição inválida";
            case 404 -> "Recurso não encontrado";
            case 405 -> "Método HTTP não permitido";
            case 406 -> "Formato de resposta não suportado";
            case 415 -> "Tipo de conteúdo não suportado";
            default -> status.is5xxServerError() ? "Erro interno do servidor" : "Requisição não permitida";
        };
        return respond(status, message, Map.of(), headers, request);
    }

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<Object> invalidCredentials(WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
        headers.setCacheControl("no-store");
        return respond(HttpStatus.UNAUTHORIZED, "Credenciais inválidas", Map.of(), headers, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> notFound(ResourceNotFoundException ex, WebRequest request) {
        return respond(HttpStatus.NOT_FOUND, ex.getMessage(), Map.of(), new HttpHeaders(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> unexpected(Exception ex, WebRequest request) {
        if (ex instanceof ErrorResponse error) {
            return respond(error.getStatusCode(), "Requisição inválida", Map.of(), error.getHeaders(), request);
        }
        log.error("Falha inesperada ao processar requisição", ex);
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor", Map.of(), new HttpHeaders(),
                request);
    }

    private ResponseEntity<Object> respond(HttpStatusCode status, String message, Map<String, String> fields,
            HttpHeaders headers, WebRequest request) {
        HttpServletRequest servlet = ((ServletWebRequest) request).getRequest();
        return new ResponseEntity<>(
                new ApiError(Instant.now(), status.value(), message, servlet.getRequestURI(), fields), headers, status);
    }
}
