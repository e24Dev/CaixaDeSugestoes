package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.persistence;

import java.time.Instant;
import jakarta.persistence.*;
@Entity
@Table(name = "suggestions")
public class SuggestionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, columnDefinition = "text") String title;
    @Column(nullable = false, columnDefinition = "text") String content;
    @Column(columnDefinition = "text") String author;
    @Column(name = "created_at", nullable = false, updatable = false) Instant createdAt;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false) CourseEntity course;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false) CategoryEntity category;
    protected SuggestionEntity() {}
}
