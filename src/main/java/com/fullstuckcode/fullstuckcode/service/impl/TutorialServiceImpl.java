package com.fullstuckcode.fullstuckcode.service.impl;

import com.fullstuckcode.fullstuckcode.constant.ResponseMessage;
import com.fullstuckcode.fullstuckcode.entity.Tutorial;
import com.fullstuckcode.fullstuckcode.exception.NotFoundException;
import com.fullstuckcode.fullstuckcode.repository.TutorialRepository;
import com.fullstuckcode.fullstuckcode.service.CategoryService;
import com.fullstuckcode.fullstuckcode.service.TagService;
import com.fullstuckcode.fullstuckcode.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TutorialServiceImpl implements TutorialService {

    private final TutorialRepository tutorialRepository;
    private final TagService tagService;
    private final CategoryService categoryService;

    @Autowired
    public TutorialServiceImpl(TutorialRepository tutorialRepository, TagService tagService, CategoryService categoryService) {
        this.tutorialRepository = tutorialRepository;
        this.tagService = tagService;
        this.categoryService = categoryService;
    }

    @Override
    public Tutorial createNew(Tutorial request) {
        request.getCategories().forEach(category -> categoryService.get(category.getId()));
        request.getTags().forEach(tag -> tagService.get(tag.getId()));

        return tutorialRepository.findByTitleIgnoreCase(request.getTitle())
                .orElseGet(() -> tutorialRepository.save(request));
    }

    @Override
    public Tutorial get(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public Page<Tutorial> getAllWithPage(Pageable pageable) {
        return tutorialRepository.findAll(pageable);
    }

    @Override
    public void update(Tutorial tutorial) {
        findByIdOrThrowNotFound(tutorial.getId());
        tutorialRepository.save(tutorial);
    }

    @Override
    public void delete(String id) {
        findByIdOrThrowNotFound(id);
        tutorialRepository.deleteById(id);
    }

    private Tutorial findByIdOrThrowNotFound(String id) {
        return tutorialRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ResponseMessage.getResourceNotFound(Tutorial.class.getSimpleName())));
    }
}
