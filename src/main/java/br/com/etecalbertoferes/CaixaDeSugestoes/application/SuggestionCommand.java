package br.com.etecalbertoferes.CaixaDeSugestoes.application;

public record SuggestionCommand(String title, String content, String author, Long courseId, Long categoryId) {}
