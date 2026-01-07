package com.aat_projects.code_review.analysis.codesmell.detectors;

import com.aat_projects.code_review.analysis.codesmell.enums.CodeSmellType;
import com.aat_projects.code_review.analysis.codesmell.enums.Severity;
import com.aat_projects.code_review.analysis.codesmell.model.CodeSmell;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeepNestingDetector implements CodeSmellDetector {

    private static final int MAX_ALLOWED_DEPTH = 3;
    @Override
    public List<CodeSmell> detect(ClassOrInterfaceDeclaration cLass) {
        List<CodeSmell> smells = new ArrayList<>();

        for(MethodDeclaration method : cLass.getMethods()){
            int depth = calculateDepth(method);
            if(depth > MAX_ALLOWED_DEPTH) {
                smells.add(CodeSmell.builder()
                        .type(CodeSmellType.DEEP_NESTING)
                        .severity(depth >= 5 ? Severity.HIGH : Severity.MEDIUM)
                        .className(cLass.getNameAsString())
                        .methodName(method.getNameAsString())
                        .lineNumber(method.getBegin().map(p -> p.line).orElse(-1))
                        .description("Nesting depth is " + depth)
                        .build());
            }
        }
        return smells;
    }

    private int calculateDepth(MethodDeclaration method){
        return method.findAll(IfStmt.class).size()
                + method.findAll(ForStmt.class).size()
                + method.findAll(WhileStmt.class).size()
                + method.findAll(SwitchStmt.class).size();
    }
}
