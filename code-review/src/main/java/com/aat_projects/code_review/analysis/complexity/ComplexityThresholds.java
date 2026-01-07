package com.aat_projects.code_review.analysis.complexity;

import com.aat_projects.code_review.analysis.complexity.enums.ComplexityRiskLevel;

public class ComplexityThresholds {
    public static ComplexityRiskLevel classify(int complexity){
        if(complexity <= 5){
            return ComplexityRiskLevel.LOW;
        }
        if(complexity <= 10){
            return ComplexityRiskLevel.MODERATE;
        }
        if(complexity <= 20){
            return ComplexityRiskLevel.HIGH;
        }
        return ComplexityRiskLevel.CRITICAL;
    }
}
