package com.fullstuckcode.fullstuckcode.service;

import com.fullstuckcode.fullstuckcode.entity.Tag;
import com.fullstuckcode.fullstuckcode.exception.NotFoundException;
import com.fullstuckcode.fullstuckcode.repository.TagRepository;
import com.fullstuckcode.fullstuckcode.service.impl.TagServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class TagServiceTest {

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private TagRepository tagRepository;

    private Tag tag;

    @BeforeEach
    void setUp() {
        tag = new Tag("Java", "java");
    }

    @AfterEach
    void tearDown() {
        tagRepository.deleteAll();
    }

    @Test
    void createNew_should_recordOneAtTable() {
        List<Tag> tags = tagService.createNew(List.of(tag));

        assertEquals(1, tags.size());
        assertEquals(tag.getName(), tagRepository.findAll().get(0).getName());
        assertEquals(tag.getSlug(), tagRepository.findAll().get(0).getSlug());
    }

    @Test
    void getById_should_returnOneTag() {
        Tag expectedTag = tagRepository.save(tag);
        Tag actualTag = tagService.get(expectedTag.getId());

        assertEquals(expectedTag.getName(), actualTag.getName());
        assertEquals(expectedTag.getSlug(), actualTag.getSlug());
        assertEquals(expectedTag.getCreatedAt(), actualTag.getCreatedAt());
    }

    @Test
    void getAll_should_returnListOfTag() {
        tagRepository.saveAll(List.of(tag, new Tag("JavaScript", "javascript")));
        List<Tag> tags = tagService.getAll();
        assertEquals(2, tags.size());
    }

    @Test
    void whenUpdateIsSuccessful() {
        Tag save = tagRepository.save(tag);
        tagService.update(new Tag(save.getId(), "JavaScript", "javascript", new Date()));
        assertEquals("JavaScript", tagRepository.findById(save.getId()).get().getName());
    }

    @Test
    void whenDeleteIsSuccessful() {
        Tag save = tagRepository.save(tag);
        tagService.delete(save.getId());
        assertEquals(0, tagRepository.findAll().size());
    }

    @Test
    void getById_should_throwNotFoundException() {
        tagRepository.save(tag);
        assertThrows(NotFoundException.class, () -> tagService.get("xxx"));
    }
}