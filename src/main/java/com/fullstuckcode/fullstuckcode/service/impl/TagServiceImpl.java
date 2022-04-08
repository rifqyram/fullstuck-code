package com.fullstuckcode.fullstuckcode.service.impl;

import com.fullstuckcode.fullstuckcode.constant.ResponseMessage;
import com.fullstuckcode.fullstuckcode.entity.Tag;
import com.fullstuckcode.fullstuckcode.exception.NotFoundException;
import com.fullstuckcode.fullstuckcode.repository.TagRepository;
import com.fullstuckcode.fullstuckcode.service.TagService;
import com.fullstuckcode.fullstuckcode.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final Utility utility;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, Utility utility) {
        this.tagRepository = tagRepository;
        this.utility = utility;
    }

    @Override
    public List<Tag> createNew(List<Tag> request) {
        return request.stream().map(reqTag -> {
            Tag tag = new Tag(reqTag.getName(), utility.toSlug(reqTag.getName()));
            return tagRepository.findByNameIgnoreCase(reqTag.getName()).orElseGet(() -> tagRepository.save(tag));
        }).collect(Collectors.toList());
    }

    @Override
    public Tag get(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public void update(Tag tag) {
        findByIdOrThrowNotFound(tag.getId());
        tagRepository.save(tag);
    }

    @Override
    public void delete(String id) {
        findByIdOrThrowNotFound(id);
        tagRepository.deleteById(id);
    }

    private Tag findByIdOrThrowNotFound(String id) {
        return tagRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ResponseMessage.getResourceNotFound(Tag.class.getSimpleName())));
    }
}
