package com.fullstuckcode.fullstuckcode.service;

import com.fullstuckcode.fullstuckcode.entity.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TutorialService {

    Tutorial createNew(Tutorial tutorial);

    Tutorial get(String id);

    Page<Tutorial> getAllWithPage(Pageable pageable);

    void update(Tutorial tutorial);

    void delete(String id);

}
