package com.fullstuckcode.fullstuckcode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstuckcode.fullstuckcode.constant.ResponseMessage;
import com.fullstuckcode.fullstuckcode.entity.Category;
import com.fullstuckcode.fullstuckcode.model.response.WebResponse;
import com.fullstuckcode.fullstuckcode.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    ObjectMapper objectMapper;

    Category category;

    @BeforeEach
    void setUp() {
        category = new Category(
                "1",
                "test",
                "test",
                new Date()
        );
    }

    @Test
    void createCategoryItShouldSaveCategoryAndReturnResponseCreated() throws Exception {
        when(categoryService.createNew(any())).thenReturn(category);

        WebResponse<?> webResponse = new WebResponse<>(
                HttpStatus.CREATED.name(),
                HttpStatus.CREATED.value(),
                ResponseMessage.getResourceCreated(Category.class.getSimpleName()),
                category
        );

        mockMvc.perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }

    @Test
    void getAllItShouldReturnListCategoryAndReturnResponseOk() throws Exception {
        List<Category> categories = new ArrayList<>(List.of(category));
        when(categoryService.getAll()).thenReturn(categories);

        WebResponse<?> webResponse = new WebResponse<>(HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ResponseMessage.getResourceGetSuccess(Category.class.getSimpleName()),
                categories
        );

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }

    @Test
    void getByIdItShouldReturnCategoryAndResponseMessageOk() throws Exception {
        when(categoryService.get(any())).thenReturn(category);

        WebResponse<?> webResponse = new WebResponse<>(HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ResponseMessage.getResourceGetSuccess(Category.class.getSimpleName()),
                category
        );

        mockMvc.perform(get("/api/v1/categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }

    @Test
    void itShouldUpdateAndReturnResponseOk() throws Exception {
        doNothing().when(categoryService).update(category);

        WebResponse<?> webResponse = new WebResponse<>(
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ResponseMessage.getResourceUpdated(Category.class.getSimpleName()),
                category.getId());

        mockMvc.perform(put("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }

    @Test
    void itShouldDeletedAndReturnResponseOk() throws Exception {
        doNothing().when(categoryService).delete(category.getId());

        WebResponse<?> webResponse = new WebResponse<>(
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ResponseMessage.getResourceDeleted(Category.class.getSimpleName()),
                category.getId());

        mockMvc.perform(delete("/api/v1/categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }
}