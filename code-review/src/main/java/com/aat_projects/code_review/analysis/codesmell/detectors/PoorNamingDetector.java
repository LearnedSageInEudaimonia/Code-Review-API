package com.aat_projects.code_review.analysis.codesmell.detectors;

import com.aat_projects.code_review.analysis.codesmell.enums.CodeSmellType;
import com.aat_projects.code_review.analysis.codesmell.enums.Severity;
import com.aat_projects.code_review.analysis.codesmell.model.CodeSmell;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.PrimitiveType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class PoorNamingDetector implements CodeSmellDetector {
    private static final Set<String> GENERIC_NAMES = Set.of("data", "info", "value","process","handle","manage");
    private static final Set<String> BOOLEAN_PREFIXES = Set.of("is","has", "can", "should");


    @Override
    public List<CodeSmell> detect(ClassOrInterfaceDeclaration cLass) {
        List<CodeSmell> smells = new ArrayList<>();
        for(MethodDeclaration method : cLass.getMethods()){
            String methodName = method.getNameAsString();

            if(method.isConstructorDeclaration()) continue;

            if(isGenericName(methodName)){
                smells.add(buildSmell(
                        cLass, method, "Method name is too generic and hides intent",
                        Severity.MEDIUM
                ));
            }

            if(returnsBoolean(method) && !hasBooleanPrefix(methodName)){
                smells.add(buildSmell(
                        cLass, method,
                        "Boolean-returning method should start with is/has/can/should",
                        Severity.MEDIUM
                ));
            }

            if(looksLikeGetterOrSetter(methodName) && !matchesGetterSetterContract(method)){
                smells.add(buildSmell(
                        cLass, method,
                        "Method name suggest getter/setter but signature does not match",
                        Severity.HIGH
                ));
            }

            if(hasUnreadableAbbreviation(methodName)){
                smells.add(buildSmell(
                        cLass, method,
                        "Method Name contains unreadable abbreviations",
                        Severity.LOW
                ));
            }
        }
        return smells;
    }
    private boolean isGenericName(String name){
        return GENERIC_NAMES.stream().anyMatch(name.toLowerCase()::contains);
    }

    private boolean returnsBoolean(MethodDeclaration method){
        return method.getType().isPrimitiveType()
                && method.getType().asPrimitiveType().getType() == PrimitiveType.Primitive.BOOLEAN;
    }

    private boolean hasBooleanPrefix(String name){
        return BOOLEAN_PREFIXES.stream().anyMatch(name::startsWith);
    }

    private boolean looksLikeGetterOrSetter(String name){
        return name.startsWith("get") || name.startsWith("set");
    }

    private boolean matchesGetterSetterContract(MethodDeclaration method){
        String name = method.getNameAsString();

        if(name.startsWith("get")){
            return method.getParameters().isEmpty()
                    && !method.getType().isVoidType();
        }

        if(name.startsWith("set")){
            return method.getParameters().size() == 1
                    && method.getType().isVoidType();
        }
        return true;
    }

    private boolean hasUnreadableAbbreviation(String name){
        return name.matches(".*[a-z]{1,2}[A-Z]{1,2}[a-z]{1,2}.*")
                && name.length() > 8;
    }

    private CodeSmell buildSmell(
            ClassOrInterfaceDeclaration cLass,
            MethodDeclaration method,
            String description,
            Severity severity
    ){
        return CodeSmell.builder()
                .type(CodeSmellType.POOR_NAMING)
                .severity(severity)
                .className(cLass.getNameAsString())
                .lineNumber(method.getBegin().map(p->p.line).orElse(-1))
                .description(description)
                .build();
    }
}

