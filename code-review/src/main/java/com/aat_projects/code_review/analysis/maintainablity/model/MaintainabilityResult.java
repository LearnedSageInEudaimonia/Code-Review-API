package com.aat_projects.code_review.analysis.maintainablity.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaintainabilityResult {
    private double score;
    private String grade;
}
