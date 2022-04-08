package com.fullstuckcode.fullstuckcode.service;

import com.fullstuckcode.fullstuckcode.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createNew(Category category);

    Category get(String id);

    List<Category> getAll();

    void update(Category category);

    void delete(String id);

}
