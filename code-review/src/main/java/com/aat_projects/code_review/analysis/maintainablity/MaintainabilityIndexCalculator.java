package com.aat_projects.code_review.analysis.maintainablity;

import com.aat_projects.code_review.analysis.codesmell.model.CodeSmell;
import com.aat_projects.code_review.analysis.maintainablity.model.MaintainabilityResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MaintainabilityIndexCalculator {
    private final MaintainabilityWeight weights;

    public MaintainabilityResult calculate(int linesOfCode, int classComplexity,
                                           List<CodeSmell> smells){
        double complexityPenalty = normalizeComplexity(classComplexity);
        double locPenalty = normalizeLoc(linesOfCode);
        double smellPenalty = normalizeSmells(smells);

        double score = 100 - (complexityPenalty * weights.getComplexity())
                - (locPenalty * weights.getLoc())
                - (smellPenalty * weights.getSmells());

        score = clamp(score);

        return MaintainabilityResult.builder()
                .score(score)
                .grade(grade(score))
                .build();
    }

    private double normalizeComplexity(int complexity){
        return Math.min(complexity * 2.0, 100);
    }

    private double normalizeLoc(int loc){
        return Math.min(loc/2.0, 100);
    }

    private double normalizeSmells(List<CodeSmell> smells){
        int penalty = 0;
        for(CodeSmell smell : smells){
            penalty += switch (smell.getSeverity()){
                case LOW -> 5;
                case MEDIUM -> 10;
                case HIGH -> 20;
            };
        }
        return Math.min(penalty, 100);
    }

    private double clamp(double value){
        return Math.max(0, Math.min(100, value));
    }

    private String grade(double score){
        if(score >= 85) return "EXCELLENT";
        if(score >= 70) return "GOOD";
        if(score >= 50) return "FAIR";
        return "POOR";
    }
}
