package example.springqueringexercise.data.services;

import example.springqueringexercise.data.entities.Category;
import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
