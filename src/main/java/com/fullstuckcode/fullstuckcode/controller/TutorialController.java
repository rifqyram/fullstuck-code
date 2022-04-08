package com.fullstuckcode.fullstuckcode.controller;

import com.fullstuckcode.fullstuckcode.constant.ResponseMessage;
import com.fullstuckcode.fullstuckcode.entity.Tutorial;
import com.fullstuckcode.fullstuckcode.model.response.PageResponse;
import com.fullstuckcode.fullstuckcode.model.response.WebResponse;
import com.fullstuckcode.fullstuckcode.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tutorial")
public class TutorialController {

    private final TutorialService tutorialService;

    @Autowired
    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }

    @PostMapping
    public ResponseEntity<WebResponse<?>> create(@RequestBody Tutorial request) {
        Tutorial tutorial = tutorialService.createNew(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new WebResponse<>(
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        ResponseMessage.getResourceGetSuccess(Tutorial.class.getSimpleName()),
                        tutorial));
    }

    @GetMapping
    public ResponseEntity<WebResponse<?>> findAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer size,
            @RequestParam(defaultValue = "createdAt") String direction,
            @RequestParam(defaultValue = "DESC") String sortBy) {

        Sort sort = Sort.by(Sort.Direction.valueOf(sortBy), direction);
        Pageable pageable = PageRequest.of((page - 1), size, sort);

        Page<Tutorial> tutorials = tutorialService.getAllWithPage(pageable);
        PageResponse<Tutorial> response = new PageResponse<>(
                tutorials.getContent(),
                tutorials.getTotalElements(),
                tutorials.getTotalPages(),
                tutorials.getNumber(),
                tutorials.getSize()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        ResponseMessage.getResourceGetSuccess(Tutorial.class.getSimpleName()),
                        response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<?>> findById(@PathVariable String id) {
        Tutorial tutorial = tutorialService.get(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        ResponseMessage.getResourceGetSuccess(Tutorial.class.getSimpleName()),
                        tutorial));
    }

    @PutMapping
    public ResponseEntity<WebResponse<?>> update(@RequestBody Tutorial request) {
        tutorialService.update(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        ResponseMessage.getResourceUpdated(Tutorial.class.getSimpleName()),
                        request.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<?>> delete(@PathVariable String id) {
        tutorialService.delete(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        ResponseMessage.getResourceDeleted(Tutorial.class.getSimpleName()),
                        id));
    }
}
