package com.aat_projects.code_review.analysis.complexity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeComplexity {
    O_1("O(1)"),
    O_N("O(n)"),
    O_N2("O(n²)"),
    O_N3("O(n³)"),
    O_NK("O(n^k)");

    private final String label;
}
