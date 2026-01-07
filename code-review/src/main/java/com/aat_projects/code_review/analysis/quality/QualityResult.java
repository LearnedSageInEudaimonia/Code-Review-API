package com.aat_projects.code_review.analysis.quality;

import com.aat_projects.code_review.analysis.quality.enums.QualityBand;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QualityResult {
    private QualityBand band;
    private String reason;

}
