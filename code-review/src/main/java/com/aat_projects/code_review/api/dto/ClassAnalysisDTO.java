package com.aat_projects.code_review.api.dto;

import com.aat_projects.code_review.analysis.codesmell.model.CodeSmell;
import com.aat_projects.code_review.analysis.complexity.model.MethodComplexity;
import com.aat_projects.code_review.analysis.maintainablity.model.MaintainabilityResult;
import com.aat_projects.code_review.analysis.quality.QualityResult;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassAnalysisDTO {
    private String className;

    private int methodCount;
    private int classComplexity;
    private double averageMethodComplexity;

    private List<MethodComplexity> methods;
    private List<ClassAnalysisDTO> innerClasses;

    private List<CodeSmell> codeSmells;

    private MaintainabilityResult maintainability;

    private QualityResult qualityResult;
}
