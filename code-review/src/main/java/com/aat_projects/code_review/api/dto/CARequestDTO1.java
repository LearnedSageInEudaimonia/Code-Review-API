package com.aat_projects.code_review.api.dto;

import com.aat_projects.code_review.analysis.complexity.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CARequestDTO1 {
    String sourcecode;
    Language language;

}
