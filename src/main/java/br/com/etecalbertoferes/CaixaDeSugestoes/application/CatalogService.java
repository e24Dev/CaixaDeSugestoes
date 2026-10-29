package br.com.etecalbertoferes.CaixaDeSugestoes.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.etecalbertoferes.CaixaDeSugestoes.domain.*;
@Service
@Transactional(readOnly = true)
public class CatalogService {
    private final CourseRepository courses;
    private final CategoryRepository categories;
    public CatalogService(CourseRepository courses, CategoryRepository categories) {
        this.courses = courses; this.categories = categories;
    }
    public List<Course> courses() { return courses.findAll(); }
    public List<Category> categories() { return categories.findAll(); }
}
