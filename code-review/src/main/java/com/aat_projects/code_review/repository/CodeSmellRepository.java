package com.aat_projects.code_review.repository;

import com.aat_projects.code_review.entity.CodeSmellEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CodeSmellRepository extends JpaRepository<CodeSmellEntity, Long> {
    List<CodeSmellEntity> findByClassAnalysisEntityId(Long classAnalysisId);
}