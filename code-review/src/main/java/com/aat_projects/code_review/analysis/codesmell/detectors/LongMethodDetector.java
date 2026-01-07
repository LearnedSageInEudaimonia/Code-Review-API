package com.aat_projects.code_review.analysis.codesmell.detectors;

import com.aat_projects.code_review.analysis.codesmell.enums.CodeSmellType;
import com.aat_projects.code_review.analysis.codesmell.enums.Severity;
import com.aat_projects.code_review.analysis.codesmell.model.CodeSmell;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LongMethodDetector implements CodeSmellDetector {
    private static final int MEDIUM_THRESHOLD = 40;
    private static final int HIGH_THRESHOLD = 60;
    @Override
    public List<CodeSmell> detect(ClassOrInterfaceDeclaration cLass) {
        List<CodeSmell> smells = new ArrayList<>();

        for(MethodDeclaration method : cLass.getMethods()){
            int loc = method.getBody()
                    .map(b -> b.getStatements().size())
                    .orElse(0);

            if(loc > MEDIUM_THRESHOLD){
                Severity severity = loc > HIGH_THRESHOLD? Severity.HIGH : Severity.MEDIUM;

                smells.add(CodeSmell.builder()
                        .type(CodeSmellType.LONG_METHOD)
                        .severity(severity)
                        .lineNumber(method.getBegin().map(p -> p.line).orElse(-1))
                        .methodName(method.getNameAsString())
                        .className(cLass.getNameAsString())
                        .description("Method has" + loc + " statements")
                        .build());
            }

        }
        return smells;
    }
}
