package com.aat_projects.code_review.api.dto;

import com.aat_projects.code_review.analysis.complexity.enums.Language;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CAResponseDTO {
    private Language language;
    private int classCount;
    private List<ClassAnalysisDTO> classes;
}
