package com.aat_projects.code_review.service.records;

import com.aat_projects.code_review.analysis.complexity.enums.Language;
import com.aat_projects.code_review.api.dto.CAResponseDTO;

public record AnalysisPersistenceRequest (
    String filename,
    Language language,

    String sourceCode,
    CAResponseDTO responseDTO
){}
