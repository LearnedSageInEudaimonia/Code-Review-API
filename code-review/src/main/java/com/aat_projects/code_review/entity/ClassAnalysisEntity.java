package com.aat_projects.code_review.entity;

import com.aat_projects.code_review.analysis.quality.enums.QualityBand;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "class_analysis_metrics")
public class ClassAnalysisEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String className;

    @Column(nullable = false)
    private int methodCount;

    @Column(nullable = false)
    private int classComplexity;

    @Column(nullable = false)
    private double averageMethodComplexity;

    @Column(nullable = false)
    private double maintainabilityScore;

    @Enumerated(EnumType.STRING)
    private QualityBand qualityBand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "analysis_run", nullable = false)
    private AnalysisRunEntity analysisRunEntity;


}
