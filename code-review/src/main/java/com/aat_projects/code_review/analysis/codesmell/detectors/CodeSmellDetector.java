package com.aat_projects.code_review.analysis.codesmell.detectors;

import com.aat_projects.code_review.analysis.codesmell.model.CodeSmell;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.util.List;

public interface CodeSmellDetector {
    List<CodeSmell> detect(ClassOrInterfaceDeclaration cLass);
}
