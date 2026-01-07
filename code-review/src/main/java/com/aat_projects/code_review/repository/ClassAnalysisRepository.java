package com.aat_projects.code_review.repository;

import com.aat_projects.code_review.entity.ClassAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface ClassAnalysisRepository extends JpaRepository<ClassAnalysisEntity, Long> {
    List<ClassAnalysisEntity> findByAnalysisRunEntityId(Long analysisRunId);
}