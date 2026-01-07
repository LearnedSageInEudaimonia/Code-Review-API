package com.aat_projects.code_review.service;

import com.aat_projects.code_review.analysis.codesmell.model.CodeSmell;
import com.aat_projects.code_review.api.dto.ClassAnalysisDTO;
import com.aat_projects.code_review.entity.AnalysisRunEntity;
import com.aat_projects.code_review.entity.ClassAnalysisEntity;
import com.aat_projects.code_review.entity.CodeSmellEntity;
import com.aat_projects.code_review.repository.ClassAnalysisRepository;
import com.aat_projects.code_review.repository.CodeSmellRepository;
import com.aat_projects.code_review.service.mapper.ClassAnalysisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClassAnalysisPersistenceHelper {

    private final ClassAnalysisRepository classAnalysisRepository;
    private final CodeSmellRepository codeSmellRepository;

    public void persistClassRecursively(ClassAnalysisDTO classAnalysisDTO,
                                        AnalysisRunEntity analysisRun){
        ClassAnalysisEntity classEntity = ClassAnalysisMapper.toEntity(classAnalysisDTO, analysisRun);

        ClassAnalysisEntity savedClass = classAnalysisRepository.save(classEntity);

        for(CodeSmell smell : classAnalysisDTO.getCodeSmells()){
            CodeSmellEntity smellEntity = CodeSmellEntity.builder()
                    .smellType(smell.getType())
                    .severity(smell.getSeverity())
                    .message(smell.getDescription())
                    .classAnalysisEntity(savedClass)
                    .build();
            codeSmellRepository.save(smellEntity);
        }

        for(ClassAnalysisDTO innerClass : classAnalysisDTO.getInnerClasses()){
            persistClassRecursively(innerClass, analysisRun);
        }
    }
}
