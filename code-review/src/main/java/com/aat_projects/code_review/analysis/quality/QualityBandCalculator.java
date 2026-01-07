package com.aat_projects.code_review.analysis.quality;

import com.aat_projects.code_review.analysis.codesmell.enums.Severity;
import com.aat_projects.code_review.analysis.codesmell.model.CodeSmell;
import com.aat_projects.code_review.analysis.maintainablity.model.MaintainabilityResult;
import com.aat_projects.code_review.analysis.quality.enums.QualityBand;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class QualityBandCalculator {
    public QualityResult calculate(
            MaintainabilityResult mi,
            List<CodeSmell> smells,
            int classComplexity,
            int methodCount
    ){
        QualityBand baseBand = bandFromMI(mi.getScore());

        if(hasHighSeveritySmell(smells)){
            return downgrade(
                    baseBand,
                    "High severity code smell detected"
            );
        }

        if(classComplexity > 50){
            return QualityResult.builder()
                    .band(QualityBand.HIGH_RISK)
                    .reason("Class complexity exceeds safe threshold").build();
        }

        if(methodCount > 0 && smells.size() > methodCount){
            return downgrade(baseBand, "code smell density is high relative to class size");
        }

        return QualityResult.builder()
                .band(baseBand)
                .reason("Quality band derived from maintainability Index")
                .build();
    }
    private QualityBand bandFromMI(double score) {
        if (score >= 85) return QualityBand.EXCELLENT;
        if (score >= 70) return QualityBand.GOOD;
        if (score >= 50) return QualityBand.NEEDS_IMPROVEMENT;
        return QualityBand.HIGH_RISK;
    }

    private boolean hasHighSeveritySmell(List<CodeSmell> smells) {
        return smells.stream()
                .anyMatch(smell -> smell.getSeverity() == Severity.HIGH);
    }

    private QualityResult downgrade(QualityBand current, String reason) {
        QualityBand downgraded = switch (current) {
            case EXCELLENT -> QualityBand.GOOD;
            case GOOD -> QualityBand.NEEDS_IMPROVEMENT;
            default -> QualityBand.HIGH_RISK;
        };

        return QualityResult.builder()
                .band(downgraded)
                .reason(reason)
                .build();
    }
}
