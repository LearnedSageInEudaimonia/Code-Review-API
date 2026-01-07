package com.aat_projects.code_review.parsing;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class JavaAstParser {

    public CompilationUnit parse(String sourceCode){
        JavaParser parser = new JavaParser();
        ParseResult<CompilationUnit> result = parser.parse(sourceCode);

        if(result.isSuccessful() && result.getResult().isPresent()){
            return result.getResult().get();
        }
        throw new IllegalArgumentException("Invalid Java source Code");
    }
}
