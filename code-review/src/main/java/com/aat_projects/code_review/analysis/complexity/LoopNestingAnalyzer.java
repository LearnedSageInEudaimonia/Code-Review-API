package com.aat_projects.code_review.analysis.complexity;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

public class LoopNestingAnalyzer {
    public int calculateMaxDepth(MethodDeclaration method){
        return calculateDepth(method.getBody().orElseThrow(), 0);
    }

    private int calculateDepth(Statement statement, int currentDepth) {
        int maxDepth = currentDepth;
        for(Statement child : statement.getChildNodesByType(Statement.class)){
                Boolean isLoop = child.isForEachStmt()
                        || (child.isForStmt())
                        || (child.isDoStmt())
                        || child.isWhileStmt();

            int depth = isLoop ? currentDepth + 1: currentDepth;
            maxDepth = Math.max(maxDepth, calculateDepth(child, depth));
        }
        return maxDepth;
    }
}
