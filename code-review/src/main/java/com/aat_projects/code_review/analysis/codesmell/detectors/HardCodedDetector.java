package com.aat_projects.code_review.analysis.codesmell.detectors;

import com.aat_projects.code_review.analysis.codesmell.enums.CodeSmellType;
import com.aat_projects.code_review.analysis.codesmell.enums.Severity;
import com.aat_projects.code_review.analysis.codesmell.model.CodeSmell;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class HardCodedDetector implements CodeSmellDetector {
    private static final Set<Integer> IGNORED = Set.of(0,1,-1);
    @Override
    public List<CodeSmell> detect(ClassOrInterfaceDeclaration cLass) {
        List<CodeSmell> smells = new ArrayList<>();
        cLass.findAll(IntegerLiteralExpr.class)
                .forEach(literal -> {
                    int value = Integer.parseInt(literal.getValue());

                    if(!IGNORED.contains(value)){
                        smells.add(CodeSmell.builder()
                                .type(CodeSmellType.HARDCODED)
                                .severity(Severity.MEDIUM)
                                .className(cLass.getNameAsString())
                                .methodName(literal.findAncestor(MethodDeclaration.class)
                                        .map(m -> m.getNameAsString()).orElse("N/A"))
                                .lineNumber(literal.getBegin().map(p -> p.line).orElse(-1))
                                .description("Harcoded Number: " + value)
                                .build());
                    }
                });
        return smells;
    }
}
