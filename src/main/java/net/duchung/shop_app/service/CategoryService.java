package net.duchung.shop_app.service;

import net.duchung.shop_app.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryDto createCategory(CategoryDto category);
    CategoryDto getCategoryById(long id);
    List<CategoryDto> getAllCategories();
    CategoryDto updateCategory(Long id, CategoryDto category);
    void deleteCategory(long id);
}
