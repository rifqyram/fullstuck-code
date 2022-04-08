package com.fullstuckcode.fullstuckcode.controller;

import com.fullstuckcode.fullstuckcode.constant.ResponseMessage;
import com.fullstuckcode.fullstuckcode.entity.Tag;
import com.fullstuckcode.fullstuckcode.service.TagService;
import com.fullstuckcode.fullstuckcode.model.response.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<WebResponse<?>> create(@RequestBody List<Tag> request) {
        List<Tag> tags = tagService.createNew(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new WebResponse<>(
                        HttpStatus.CREATED.name(),
                        HttpStatus.CREATED.value(),
                        ResponseMessage.getResourceCreated(Tag.class.getSimpleName()),
                        tags));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<?>> getById(@PathVariable String id) {
        Tag tag = tagService.get(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        ResponseMessage.getResourceGetSuccess(Tag.class.getSimpleName()),
                        tag));
    }

    @GetMapping
    public ResponseEntity<WebResponse<?>> getAll() {
        List<Tag> tags = tagService.getAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        ResponseMessage.getResourceGetSuccess(Tag.class.getSimpleName()),
                        tags));
    }

    @PutMapping
    public ResponseEntity<WebResponse<?>> update(@RequestBody Tag request) {
        tagService.update(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        ResponseMessage.getResourceUpdated(Tag.class.getSimpleName()),
                        request.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<?>> delete(@PathVariable String id) {
        tagService.delete(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        ResponseMessage.getResourceDeleted(Tag.class.getSimpleName()),
                        id));
    }

}
