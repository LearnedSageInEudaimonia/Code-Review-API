package com.aat_projects.code_review.analysis.complexity.model;

import com.aat_projects.code_review.analysis.complexity.ComplexityThresholds;
import com.aat_projects.code_review.analysis.complexity.TimeComplexityClassifier;
import com.aat_projects.code_review.analysis.complexity.enums.ComplexityRiskLevel;
import com.aat_projects.code_review.analysis.complexity.enums.TimeComplexity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MethodComplexity {
    private String methodName;
    private int complexity;
    private ComplexityRiskLevel riskLevel;
    private TimeComplexity timeComplexity;

    public MethodComplexity(String methodName, int complexity, int loopDepth){
        this.methodName = methodName;
        this.complexity = complexity;
        this.riskLevel = ComplexityThresholds.classify(complexity);
        this.timeComplexity = TimeComplexityClassifier.classify(loopDepth);
    }
}
