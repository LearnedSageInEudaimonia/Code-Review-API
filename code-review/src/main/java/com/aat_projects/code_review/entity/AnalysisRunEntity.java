package com.aat_projects.code_review.entity;

import com.aat_projects.code_review.analysis.complexity.enums.Language;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "analysis_run",
uniqueConstraints = {
        @UniqueConstraint(
                name = "filename_content_hash",
                columnNames = {"file_name", "content_hash"}
        )
}
)
public class AnalysisRunEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "file_name" ,nullable = false)
    private String fileName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "content_hash" , nullable = false)
    private String contentHash;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime localDateTime;

}
