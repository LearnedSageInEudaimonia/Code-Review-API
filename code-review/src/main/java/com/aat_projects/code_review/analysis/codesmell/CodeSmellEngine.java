package com.aat_projects.code_review.analysis.codesmell;

import com.aat_projects.code_review.analysis.codesmell.detectors.CodeSmellDetector;
import com.aat_projects.code_review.analysis.codesmell.model.CodeSmell;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
@RequiredArgsConstructor
public class CodeSmellEngine {
    private final List<CodeSmellDetector> detectors;

    public List<CodeSmell> analyze(ClassOrInterfaceDeclaration cLass){
        return detectors.stream()
                .flatMap(detectors -> detectors.detect(cLass).stream())
                .toList();
    }
}
