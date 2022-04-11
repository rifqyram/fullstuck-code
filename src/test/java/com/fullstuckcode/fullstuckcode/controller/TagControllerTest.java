package com.fullstuckcode.fullstuckcode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstuckcode.fullstuckcode.constant.ResponseMessage;
import com.fullstuckcode.fullstuckcode.entity.Tag;
import com.fullstuckcode.fullstuckcode.exception.NotFoundException;
import com.fullstuckcode.fullstuckcode.model.response.WebResponse;
import com.fullstuckcode.fullstuckcode.service.TagService;
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
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @Autowired
    ObjectMapper objectMapper;

    Tag tag;

    @BeforeEach
    void setUp() {
        tag = new Tag("1", "Unit Testing", "unit-testing", new Date());
    }

    @Test
    void createTagItShouldSaveTagAndReturnStatusCreated() throws Exception {
        List<Tag> tags = List.of(this.tag);

        when(tagService.createNew(any())).thenReturn(tags);
        String tagJson = objectMapper.writeValueAsString(tags);

        WebResponse<List<Tag>> webResponse = new WebResponse<>(
                HttpStatus.CREATED.name(),
                HttpStatus.CREATED.value(),
                ResponseMessage.getResourceCreated(Tag.class.getSimpleName()),
                tags
        );

        String webResponseJson = objectMapper.writeValueAsString(webResponse);

        mockMvc.perform(post("/api/v1/tags")
                        .content(tagJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(webResponseJson));

    }

    @Test
    void getByIdShouldReturnStatusOK() throws Exception {
        when(tagService.get(any())).thenReturn(tag);

        WebResponse<?> webResponse = new WebResponse<>(
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ResponseMessage.getResourceGetSuccess(Tag.class.getSimpleName()),
                tag
        );

        mockMvc.perform(get("/api/v1/tags/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }

    @Test
    void getAllItShouldReturnStatusOK() throws Exception {
        List<Tag> tags = new ArrayList<>(List.of(tag));
        when(tagService.getAll()).thenReturn(tags);

        WebResponse<?> webResponse = new WebResponse<>(
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ResponseMessage.getResourceGetSuccess(Tag.class.getSimpleName()),
                tags
        );

        mockMvc.perform(get("/api/v1/tags"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }

    @Test
    void updateItShouldReturnStatusOK() throws Exception {
        doNothing().when(tagService).update(tag);

        WebResponse<?> webResponse = new WebResponse<>(
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ResponseMessage.getResourceUpdated(Tag.class.getSimpleName()),
                tag.getId());

        mockMvc.perform(put("/api/v1/tags")
                        .content(objectMapper.writeValueAsString(tag))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));

    }

    @Test
    void deleteItShouldReturnOK() throws Exception {
        doNothing().when(tagService).delete(tag.getId());

        WebResponse<?> webResponse = new WebResponse<>(
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                ResponseMessage.getResourceDeleted(Tag.class.getSimpleName()),
                tag.getId());

                mockMvc.perform(delete("/api/v1/tags/1"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }

    @Test
    void getItShouldReturnResponseNotFound() throws Exception {
        when(tagService.get(any())).thenThrow(new NotFoundException(ResponseMessage.getResourceNotFound(Tag.class.getSimpleName())));

        WebResponse<?> webResponse = new WebResponse<>(
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value(),
                ResponseMessage.getResourceNotFound(Tag.class.getSimpleName()),
                null);

        mockMvc.perform(get("/api/v1/tags/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(webResponse)));
    }
}