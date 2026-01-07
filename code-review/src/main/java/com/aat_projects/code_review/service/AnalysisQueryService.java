package com.aat_projects.code_review.service;


import com.aat_projects.code_review.analysis.codesmell.model.CodeSmell;
import com.aat_projects.code_review.analysis.maintainablity.model.MaintainabilityResult;
import com.aat_projects.code_review.analysis.quality.QualityResult;
import com.aat_projects.code_review.api.dto.CAResponseDTO;
import com.aat_projects.code_review.api.dto.ClassAnalysisDTO;
import com.aat_projects.code_review.api.dto.ResponseDTO;
import com.aat_projects.code_review.entity.AnalysisRunEntity;
import com.aat_projects.code_review.entity.ClassAnalysisEntity;
import com.aat_projects.code_review.entity.CodeSmellEntity;
import com.aat_projects.code_review.exception.AnalysisNotFoundException;
import com.aat_projects.code_review.repository.AnalysisRunRepository;
import com.aat_projects.code_review.repository.ClassAnalysisRepository;
import com.aat_projects.code_review.repository.CodeSmellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalysisQueryService {
    private final AnalysisRunRepository analysisRunRepository;
    private final ClassAnalysisRepository classAnalysisRepository;
    private final CodeSmellRepository codeSmellRepository;

    public ResponseDTO fetchAnalysis(Long analysisRunId){
        AnalysisRunEntity run = analysisRunRepository.findById(analysisRunId)
                .orElseThrow(() -> new AnalysisNotFoundException(analysisRunId));

        List<ClassAnalysisDTO> classDtos = classAnalysisRepository.findByAnalysisRunEntityId(run.getId())
                .stream()
                .map(this::mapClass)
                .toList();
        System.out.println("Entity fetched for id: " + run.getId());

        CAResponseDTO dto = CAResponseDTO.builder()
                .classCount(classDtos.size())
                .language(run.getLanguage())
                .classes(classDtos)
                .build();

        return ResponseDTO.builder()
                .id(run.getId())
                .dto(dto)
                .build();
    }

    private ClassAnalysisDTO mapClass(ClassAnalysisEntity entity){
        List<CodeSmell> smells = codeSmellRepository.findByClassAnalysisEntityId(entity.getId())
                .stream()
                .map(this::mapSmell)
                .toList();

        return ClassAnalysisDTO.builder()
                .className(entity.getClassName())
                .methodCount(entity.getMethodCount())
                .classComplexity(entity.getClassComplexity())
                .averageMethodComplexity(entity.getAverageMethodComplexity())
                .maintainability(MaintainabilityResult.builder()
                        .score(entity.getMaintainabilityScore()).build())
                .qualityResult(QualityResult.builder()
                        .band(entity.getQualityBand())
                        .build())
                .codeSmells(smells)
                .build();
    }

    private CodeSmell mapSmell(CodeSmellEntity entity){
        return CodeSmell.builder()
                .type(entity.getSmellType())
                .severity(entity.getSeverity())
                .description(entity.getMessage())
                .build();
    }

}
