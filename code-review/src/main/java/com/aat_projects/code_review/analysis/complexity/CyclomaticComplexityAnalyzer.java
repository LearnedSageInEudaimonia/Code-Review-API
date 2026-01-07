package com.aat_projects.code_review.analysis.complexity;

import com.aat_projects.code_review.analysis.complexity.model.MethodComplexity;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.stmt.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CyclomaticComplexityAnalyzer {
    public List<MethodComplexity> analyze(List<MethodDeclaration> methods) {
        List<MethodComplexity> results = new ArrayList<>();
        LoopNestingAnalyzer analyzer = new LoopNestingAnalyzer();

        for (MethodDeclaration method : methods) {
            int complexity = 1;

            complexity += method.findAll(IfStmt.class).size();
            complexity += method.findAll(ForStmt.class).size();
            complexity += method.findAll(ForEachStmt.class).size();
            complexity += method.findAll(WhileStmt.class).size();
            complexity += method.findAll(DoStmt.class).size();
            complexity += method.findAll(CatchClause.class).size();
            complexity += (int) method.findAll(SwitchEntry.class).stream()
                    .filter(e -> !e.getLabels().isEmpty()).count();
            complexity += method.findAll(ConditionalExpr.class).size();


            for (BinaryExpr expr : method.findAll(BinaryExpr.class)) {
                if (expr.getOperator() == BinaryExpr.Operator.AND ||
                        expr.getOperator() == BinaryExpr.Operator.OR) {
                    complexity++;
                }
            }
            int depth = analyzer.calculateMaxDepth(method);
            results.add(new MethodComplexity(method.getNameAsString(), complexity,depth));
        }
        return results;
    }
}
