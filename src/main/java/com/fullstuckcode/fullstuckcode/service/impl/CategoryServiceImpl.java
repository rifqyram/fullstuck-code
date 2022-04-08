package com.fullstuckcode.fullstuckcode.service.impl;

import com.fullstuckcode.fullstuckcode.constant.ResponseMessage;
import com.fullstuckcode.fullstuckcode.entity.Category;
import com.fullstuckcode.fullstuckcode.exception.NotFoundException;
import com.fullstuckcode.fullstuckcode.repository.CategoryRepository;
import com.fullstuckcode.fullstuckcode.service.CategoryService;
import com.fullstuckcode.fullstuckcode.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Utility utility;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, Utility utility) {
        this.categoryRepository = categoryRepository;
        this.utility = utility;
    }

    @Override
    public Category createNew(Category request) {
        Category category = new Category(request.getName(), utility.toSlug(request.getName()));
        Optional<Category> currentCategory = categoryRepository.findByNameIgnoreCase(request.getName());
        return currentCategory.orElseGet(() -> categoryRepository.save(category));
    }

    @Override
    public Category get(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void update(Category category) {
        findByIdOrThrowNotFound(category.getId());
        categoryRepository.save(category);
    }

    @Override
    public void delete(String id) {
        findByIdOrThrowNotFound(id);
        categoryRepository.deleteById(id);
    }

    private Category findByIdOrThrowNotFound(String id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ResponseMessage.getResourceNotFound(Category.class.getSimpleName())));
    }
}
