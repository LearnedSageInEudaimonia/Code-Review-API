package com.aat_projects.code_review.service.mapper;

import com.aat_projects.code_review.api.dto.ClassAnalysisDTO;
import com.aat_projects.code_review.entity.AnalysisRunEntity;
import com.aat_projects.code_review.entity.ClassAnalysisEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClassAnalysisMapper {

    public static ClassAnalysisEntity toEntity(ClassAnalysisDTO dto, AnalysisRunEntity analysisRun){
        return ClassAnalysisEntity.builder()
                .className(dto.getClassName())
                .methodCount(dto.getMethodCount())
                .classComplexity(dto.getClassComplexity())
                .averageMethodComplexity(dto.getAverageMethodComplexity())
                .maintainabilityScore(dto.getMaintainability().getScore())
                .qualityBand(dto.getQualityResult().getBand())
                .analysisRunEntity(analysisRun)
                .build();
    }
}
