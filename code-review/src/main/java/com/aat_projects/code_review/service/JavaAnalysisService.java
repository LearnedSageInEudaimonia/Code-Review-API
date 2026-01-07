package com.aat_projects.code_review.service;

import com.aat_projects.code_review.analysis.codesmell.CodeSmellEngine;
import com.aat_projects.code_review.analysis.codesmell.model.CodeSmell;
import com.aat_projects.code_review.analysis.complexity.CyclomaticComplexityAnalyzer;
import com.aat_projects.code_review.analysis.complexity.model.MethodComplexity;
import com.aat_projects.code_review.analysis.maintainablity.MaintainabilityIndexCalculator;
import com.aat_projects.code_review.analysis.maintainablity.model.MaintainabilityResult;
import com.aat_projects.code_review.analysis.quality.QualityBandCalculator;
import com.aat_projects.code_review.analysis.quality.QualityResult;
import com.aat_projects.code_review.api.dto.CAResponseDTO;
import com.aat_projects.code_review.api.dto.ClassAnalysisDTO;
import com.aat_projects.code_review.analysis.complexity.enums.Language;
import com.aat_projects.code_review.api.dto.ResponseDTO;
import com.aat_projects.code_review.parsing.JavaAstParser;
import com.aat_projects.code_review.repository.AnalysisRunRepository;
import com.aat_projects.code_review.service.records.AnalysisPersistenceRequest;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;



@Service
@RequiredArgsConstructor
public class JavaAnalysisService {
    private final JavaAstParser parser;
    private final CyclomaticComplexityAnalyzer complexityAnalyzer;
    private final CodeSmellEngine codeSmellEngine;
    private final MaintainabilityIndexCalculator maintainabilityIndexCalculator;
    private final QualityBandCalculator qualityBandCalculator;
    private final AnalysisPersistenceService service;
    private final AnalysisRunRepository analysisRunRepository;

    public CAResponseDTO parseJava(String sourceCode) {
        CompilationUnit code = parser.parse(sourceCode);

        List<ClassAnalysisDTO> classAnalysis = code.getTypes()
                .stream()
                .filter(ClassOrInterfaceDeclaration.class::isInstance)
                .map(ClassOrInterfaceDeclaration.class::cast)
                .map(this::analyzeClassRecursively)
                .toList();


        return CAResponseDTO.builder()
                .language(Language.JAVA)
                .classCount(classAnalysis.size())
                .classes(classAnalysis)
                .build();

    }

    private ClassAnalysisDTO analyzeClassRecursively(ClassOrInterfaceDeclaration cLass) {
        List<MethodComplexity> methodComplexities = complexityAnalyzer.analyze(cLass.getMethods());

        int classComplexity = methodComplexities.stream()
                .mapToInt(MethodComplexity::getComplexity)
                .sum();

        double avgComplexity = methodComplexities.isEmpty() ? 0.0 : (double)classComplexity / methodComplexities.size() ;
        avgComplexity = ((long)(avgComplexity * 100)) / 100.0;

        List<ClassAnalysisDTO> innerClasses = cLass.getMembers()
                .stream()
                .filter(ClassOrInterfaceDeclaration.class::isInstance)
                .map(ClassOrInterfaceDeclaration.class::cast)
                .map(this::analyzeClassRecursively)
                .toList();

        List<CodeSmell> codeSmells = codeSmellEngine.analyze(cLass);
        int loc = calculateLinesOfCode(cLass);
        MaintainabilityResult maintainability =
                maintainabilityIndexCalculator.calculate(
                        loc,
                        classComplexity,
                        codeSmells
                );

        QualityResult qualityResult = qualityBandCalculator.calculate(
                maintainability,
                codeSmells,
                classComplexity,
                methodComplexities.size()
        );
        return ClassAnalysisDTO.builder()
                .className(cLass.getNameAsString())
                .methodCount(methodComplexities.size())
                .classComplexity(classComplexity)
                .averageMethodComplexity(avgComplexity)
                .methods(methodComplexities)
                .innerClasses(innerClasses)
                .codeSmells(codeSmells)
                .maintainability(maintainability)
                .qualityResult(qualityResult)
                .build();
    }

    private int calculateLinesOfCode(ClassOrInterfaceDeclaration cLass){
        if(cLass.getBegin().isEmpty() || cLass.getEnd().isEmpty()){
            return 0;
        }
        return cLass.getEnd().get().line - cLass.getBegin().get().line + 1;
    }
    public ResponseDTO parseJava(MultipartFile file) throws IOException {
        String sourceCode = new String(file.getBytes(), StandardCharsets.UTF_8);
        CAResponseDTO dto = parseJava(sourceCode);
        Long id = save(helper(file,sourceCode,dto));
        return ResponseDTO.builder()
                .id(id)
                .dto(dto)
                .build();
    }

    public AnalysisPersistenceRequest helper(MultipartFile file, String sourceCode, CAResponseDTO dto){
        return new AnalysisPersistenceRequest(file.getOriginalFilename(), Language.JAVA,sourceCode,dto);
    }

    private Long save(AnalysisPersistenceRequest request){
       return service.persistAnalysis(request);
    }

}
