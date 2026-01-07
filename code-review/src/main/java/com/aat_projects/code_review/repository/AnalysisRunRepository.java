package com.aat_projects.code_review.repository;

import com.aat_projects.code_review.entity.AnalysisRunEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface AnalysisRunRepository extends JpaRepository<AnalysisRunEntity, Long> {
    Optional<AnalysisRunEntity> findByFileNameAndContentHash(String fileName, String contentHash);
}