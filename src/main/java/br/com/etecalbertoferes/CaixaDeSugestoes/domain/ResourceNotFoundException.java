package br.com.etecalbertoferes.CaixaDeSugestoes.domain;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { super(message); }
}
