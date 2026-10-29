package br.com.etecalbertoferes.CaixaDeSugestoes.domain;

import java.time.Instant;
public record Suggestion(Long id, String title, String content, String author,
                         Instant createdAt, Course course, Category category) {}
