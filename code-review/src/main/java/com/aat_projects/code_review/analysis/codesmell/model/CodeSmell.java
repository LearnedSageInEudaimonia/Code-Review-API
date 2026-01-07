package com.aat_projects.code_review.analysis.codesmell.model;

import com.aat_projects.code_review.analysis.codesmell.enums.CodeSmellType;
import com.aat_projects.code_review.analysis.codesmell.enums.Severity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeSmell {
    private CodeSmellType type;
    private Severity severity;
    private String className;
    private String methodName;
    private int lineNumber;
    private String description;
}
