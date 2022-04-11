package com.fullstuckcode.fullstuckcode.service;

import com.fullstuckcode.fullstuckcode.entity.Category;
import com.fullstuckcode.fullstuckcode.entity.Tag;
import com.fullstuckcode.fullstuckcode.entity.Tutorial;
import com.fullstuckcode.fullstuckcode.exception.NotFoundException;
import com.fullstuckcode.fullstuckcode.repository.TutorialRepository;
import com.fullstuckcode.fullstuckcode.service.impl.CategoryServiceImpl;
import com.fullstuckcode.fullstuckcode.service.impl.TagServiceImpl;
import com.fullstuckcode.fullstuckcode.service.impl.TutorialServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TutorialServiceTest {

    private TutorialService tutorialService;

    @Mock
    private TagService tagService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private TutorialRepository tutorialRepository;

    private Tutorial tutorial;

    @BeforeEach
    void setUp() {
        Category category = new Category("1", "category", "description", new Date());
        Tag tag = new Tag("1", "tag", "description", new Date());
        tutorial = new Tutorial("1",
                "title",
                "description",
                true,
                new Date(),
                new Date(),
                Set.of(category),
                Set.of(tag));

        tutorialService = new TutorialServiceImpl(tutorialRepository, tagService, categoryService);
    }

    @Test
    void itShouldSaveTutorialAndReturnTutorial() {
        when(tutorialRepository.save(tutorial)).thenReturn(tutorial);
        Tutorial expectedTutorial = tutorialService.createNew(tutorial);

        verify(tutorialRepository, times(1)).save(tutorial);
        assertEquals(expectedTutorial.getContent(), tutorial.getContent());
    }

    @Test
    void itShouldReturnTutorialWhenTitleIfExist() {
        when(tutorialRepository.findByTitleIgnoreCase(tutorial.getTitle())).thenReturn(Optional.of(tutorial));
        tutorialService.createNew(tutorial);
        verify(tutorialRepository, times(1)).findByTitleIgnoreCase(tutorial.getTitle());
    }

    @Test
    void itShouldGetTutorialWhenIdExist() {
        when(tutorialRepository.findById(tutorial.getId())).thenReturn(Optional.of(tutorial));
        Tutorial expectedTutorial = tutorialService.get(this.tutorial.getId());

        verify(tutorialRepository, times(1)).findById(this.tutorial.getId());
        assertEquals(expectedTutorial.getContent(), tutorial.getContent());
    }

    @Test
    void itShouldThrowNotFoundWhenGetTutorialById() {
        when(tutorialRepository.findById(tutorial.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> tutorialService.get(tutorial.getId()));
    }

    @Test
    void itShouldReturnPageOfTutorialWhenGetListWithPage() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Tutorial> tutorials = new PageImpl<>(List.of(tutorial), pageRequest, 1);

        when(tutorialRepository.findAll(isA(Pageable.class))).thenReturn(tutorials);
        Page<Tutorial> tutorialPage = tutorialService.getAllWithPage(pageRequest);

        verify(tutorialRepository, times(1)).findAll(pageRequest);
        assertEquals(tutorialPage.getTotalElements(), tutorialPage.getTotalElements());
        assertEquals(tutorialPage.getContent(), tutorials.getContent());
    }

    @Test
    void itShouldUpdateTutorial() {
        Tutorial updateTutorial = new Tutorial(
                tutorial.getId(),
                "tutorial",
                "description",
                true,
                new Date(),
                new Date(),
                Set.of(new Category("1", "category", "description", new Date())),
                Set.of(new Tag("1", "tag", "description", new Date())));
        when(tutorialRepository.findById(tutorial.getId())).thenReturn(Optional.of(tutorial));
        when(tutorialRepository.save(updateTutorial)).thenReturn(updateTutorial);

        tutorialService.update(updateTutorial);
        verify(tutorialRepository, times(1)).save(updateTutorial);
    }

    @Test
    void itShouldDeleteTutorial() {
        when(tutorialRepository.findById(tutorial.getId())).thenReturn(Optional.of(tutorial));
        tutorialService.delete(tutorial.getId());
        verify(tutorialRepository, times(1)).deleteById(tutorial.getId());
    }
}