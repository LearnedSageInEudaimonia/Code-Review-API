package com.aat_projects.code_review.analysis.maintainablity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "maintainability.weights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintainabilityWeight {
    private double complexity;
    private double loc;
    private double smells;
}
