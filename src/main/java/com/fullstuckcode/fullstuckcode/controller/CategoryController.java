package com.fullstuckcode.fullstuckcode.controller;

import com.fullstuckcode.fullstuckcode.constant.ResponseMessage;
import com.fullstuckcode.fullstuckcode.entity.Category;
import com.fullstuckcode.fullstuckcode.service.CategoryService;
import com.fullstuckcode.fullstuckcode.model.response.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<WebResponse<?>> create(@RequestBody Category request) {
        Category category = categoryService.createNew(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new WebResponse<>(HttpStatus.CREATED.name(),
                        HttpStatus.CREATED.value(),
                        ResponseMessage.getResourceCreated(Category.class.getSimpleName()),
                        category)
                );
    }

    @GetMapping
    public ResponseEntity<WebResponse<?>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        ResponseMessage.getResourceGetSuccess(Category.class.getSimpleName()),
                        categoryService.getAll()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<?>> getById(@PathVariable String id) {
        Category category = categoryService.get(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        ResponseMessage.getResourceGetSuccess(Category.class.getSimpleName()),
                        category)
                );
    }

    @PutMapping
    public ResponseEntity<WebResponse<?>> update(@RequestBody Category request) {
        categoryService.update(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        ResponseMessage.getResourceUpdated(Category.class.getSimpleName()), request.getId())
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<?>> delete(@PathVariable String id) {
        categoryService.delete(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        ResponseMessage.getResourceDeleted(Category.class.getSimpleName()), id)
                );
    }

}
