package com.fullstuckcode.fullstuckcode.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstuckcode.fullstuckcode.constant.ResponseMessage;
import com.fullstuckcode.fullstuckcode.entity.Category;
import com.fullstuckcode.fullstuckcode.entity.Tag;
import com.fullstuckcode.fullstuckcode.entity.Tutorial;
import com.fullstuckcode.fullstuckcode.model.response.PageResponse;
import com.fullstuckcode.fullstuckcode.model.response.WebResponse;
import com.fullstuckcode.fullstuckcode.service.TutorialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TutorialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TutorialService tutorialService;

    private Tutorial tutorial;

    @BeforeEach
    void setUp() {
        Category category = new Category("1", "Java", "java", new Date());
        Tag tag = new Tag("1", "Spring", "spring", new Date());
        tutorial = new Tutorial(
                "1",
                "Java Spring Boot",
                "This is a tutorial on how to build a Spring Boot application",
                true,
                new Date(),
                new Date(),
                Set.of(category),
                Set.of(tag));
    }

    @Test
    void itShouldCreateTutorialAndReturnResponseCreated() throws Exception {
        when(tutorialService.createNew(any())).thenReturn(tutorial);

        WebResponse<?> webResponse = new WebResponse<>(
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ResponseMessage.getResourceGetSuccess(Tutorial.class.getSimpleName()),
                tutorial
        );

        mockMvc.perform(post("/api/v1/tutorials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tutorial)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }

    @Test
    void findAllItShouldReturnPageTutorialAndReturnResponseOk() throws Exception {
        Page<Tutorial> tutorials = new PageImpl<>(List.of(tutorial), PageRequest.of(0, 10), 1);
        when(tutorialService.getAllWithPage(any())).thenReturn(tutorials);

        PageResponse<?> pageResponse = new PageResponse<>(
                tutorials.getContent(),
                tutorials.getTotalElements(),
                tutorials.getTotalPages(),
                tutorials.getNumber(),
                tutorials.getSize()
        );

        WebResponse<?> webResponse = new WebResponse<>(
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ResponseMessage.getResourceGetSuccess(Tutorial.class.getSimpleName()),
                pageResponse);

        mockMvc.perform(get("/api/v1/tutorials"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }

    @Test
    void findByIdItShouldReturnTutorialAndResponseOk() throws Exception {
        when(tutorialService.get(any())).thenReturn(tutorial);

        WebResponse<?> webResponse = new WebResponse<>(
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ResponseMessage.getResourceGetSuccess(Tutorial.class.getSimpleName()),
                tutorial
        );

        mockMvc.perform(get("/api/v1/tutorials/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }

    @Test
    void shouldUpdateAndReturnResponseIsOk() throws Exception {
        doNothing().when(tutorialService).update(tutorial);

        WebResponse<?> webResponse = new WebResponse<>(
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ResponseMessage.getResourceUpdated(Tutorial.class.getSimpleName()),
                tutorial.getId()
        );

        mockMvc.perform(put("/api/v1/tutorials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tutorial)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }

    @Test
    void itShouldDeleteAndReturnResponseIsOk() throws Exception {
        doNothing().when(tutorialService).delete(tutorial.getId());

        WebResponse<?> webResponse = new WebResponse<>(
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ResponseMessage.getResourceDeleted(Tutorial.class.getSimpleName()),
                tutorial.getId()
        );

        mockMvc.perform(delete("/api/v1/tutorials/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }
}