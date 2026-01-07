package com.aat_projects.code_review.analysis.complexity;

import com.aat_projects.code_review.analysis.complexity.enums.TimeComplexity;

public class TimeComplexityClassifier {
    public static TimeComplexity classify(int loopDepth){
        return switch (loopDepth){
            case 0 -> TimeComplexity.O_1;
            case 1 -> TimeComplexity.O_N;
            case 2 -> TimeComplexity.O_N2;
            case 3 -> TimeComplexity.O_N3;
            default -> TimeComplexity.O_NK;
        };
    }
}
