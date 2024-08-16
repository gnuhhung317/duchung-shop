package net.duchung.shop_app.service.impl;

import net.duchung.shop_app.dto.CategoryDto;
import net.duchung.shop_app.entity.Category;
import net.duchung.shop_app.repository.CategoryRepository;
import net.duchung.shop_app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto category) {
        return toDto(categoryRepository.save(toEntity(category)));
    }

    @Override
    @Transactional

    public CategoryDto getCategoryById(long id) {

        return toDto(categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found")));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return toDto(category);
    }

    @Override
    @Transactional
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
    public CategoryDto toDto(Category category){

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        categoryDto.setId(category.getId());
        return categoryDto;
    }
    public Category toEntity(CategoryDto categoryDto){
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setId(categoryDto.getId());
        return category;
    }
}
