package com.fullstuckcode.fullstuckcode.service;

import com.fullstuckcode.fullstuckcode.FullstuckCodeApplication;
import com.fullstuckcode.fullstuckcode.entity.Category;
import com.fullstuckcode.fullstuckcode.exception.NotFoundException;
import com.fullstuckcode.fullstuckcode.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    Category category;

    @BeforeEach
    void setUp() {
        category = new Category("Tutorial", "Tutorial");
    }

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @Test
    void createNew_should_addOneRecordAtTable() {
        categoryService.createNew(category);
        assertEquals(1, categoryRepository.findAll().size());
    }

    @Test
    void getById_should_returnOneCategory() {
        Category actualCategory = categoryRepository.save(category);
        Category expectedCategory = categoryService.get(actualCategory.getId());
        assertEquals(actualCategory.getId(), expectedCategory.getId());
        assertEquals(actualCategory.getCreatedAt(), expectedCategory.getCreatedAt());
    }

    @Test
    void getAll_shouldReturnListOfCategory() {
        List<Category> actualCategory = categoryRepository.saveAll(List.of(
                category,
                new Category("Database", "database")
        ));

        List<Category> expectedCategory = categoryService.getAll();
        assertEquals(actualCategory.size(), expectedCategory.size());
    }

    @Test
    void whenUpdateIsSuccessful() {
        Category save = categoryRepository.save(category);
        categoryService.update(new Category(save.getId(), "Programming", "programming", new Date()));
        Category actualCategory = categoryRepository.findById(save.getId()).get();
        assertEquals("programming", actualCategory.getSlug());
    }

    @Test
    void whenDeleteIsSuccessful() {
        Category save = categoryRepository.save(category);
        categoryService.delete(save.getId());
        assertEquals(0, categoryRepository.findAll().size());
    }

    @Test
    void getCategoryById_should_throwNotFoundException() {
        categoryRepository.save(category);
        assertThrows(NotFoundException.class, () -> categoryService.get("1"));
    }
}