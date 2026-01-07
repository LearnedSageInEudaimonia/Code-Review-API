package com.aat_projects.code_review.service;

import com.aat_projects.code_review.api.dto.ClassAnalysisDTO;
import com.aat_projects.code_review.entity.AnalysisRunEntity;
import com.aat_projects.code_review.exception.AnalysisNotFoundException;
import com.aat_projects.code_review.exception.GlobalExceptionHandler;
import com.aat_projects.code_review.exception.PersistenceException;
import com.aat_projects.code_review.repository.AnalysisRunRepository;
import com.aat_projects.code_review.service.records.AnalysisPersistenceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnalysisPersistenceService {

    private final AnalysisRunRepository analysisRunRepository;
    private final ClassAnalysisPersistenceHelper classAnalysisHelper;
    private final GlobalExceptionHandler exceptionHandler;

    @Transactional
    public Long persistAnalysis(AnalysisPersistenceRequest request){

        String contentHash = HashUtil.sha256(request.sourceCode());
        AnalysisRunEntity analysisRun = AnalysisRunEntity.builder()
                .fileName(request.filename())
                .language(request.language())
                .contentHash(contentHash)
                .build();

        AnalysisRunEntity savedRun;

        try{
            savedRun = analysisRunRepository.save(analysisRun);
        }catch (DataIntegrityViolationException ex){
            savedRun = analysisRunRepository.findByFileNameAndContentHash(request.filename(), contentHash)
                    .orElseThrow(() ->new AnalysisNotFoundException("File Name and Hash content cannot be retrieved:") );
            return savedRun.getId();
        }
        for(ClassAnalysisDTO classAnalysisDTO : request.responseDTO().getClasses()){
            classAnalysisHelper.persistClassRecursively(classAnalysisDTO, savedRun);
        }
        return savedRun.getId();
    }
}
