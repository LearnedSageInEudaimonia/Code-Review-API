package com.aat_projects.code_review.entity;

import com.aat_projects.code_review.analysis.codesmell.enums.CodeSmellType;
import com.aat_projects.code_review.analysis.codesmell.enums.Severity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "code_smell_metrics")
public class CodeSmellEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CodeSmellType smellType;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "class_analysis", nullable = false)
    private ClassAnalysisEntity classAnalysisEntity;
}
