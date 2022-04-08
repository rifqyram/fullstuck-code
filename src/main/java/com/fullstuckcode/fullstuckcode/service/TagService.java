package com.fullstuckcode.fullstuckcode.service;

import com.fullstuckcode.fullstuckcode.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> createNew(List<Tag> tag);

    Tag get(String id);

    List<Tag> getAll();

    void update(Tag tag);

    void delete(String id);
}
