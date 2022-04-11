package com.fullstuckcode.fullstuckcode.repository;

import com.fullstuckcode.fullstuckcode.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, String> {
    Optional<Tutorial> findByTitleIgnoreCase(String title);
}
