package com.aat_projects.code_review.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO {
    Long id;
    CAResponseDTO dto;
}
