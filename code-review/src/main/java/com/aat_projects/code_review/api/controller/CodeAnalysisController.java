package com.aat_projects.code_review.api.controller;

import com.aat_projects.code_review.api.dto.CARequestDTO;
import com.aat_projects.code_review.api.dto.CARequestDTO1;
import com.aat_projects.code_review.api.dto.CAResponseDTO;
import com.aat_projects.code_review.api.dto.ResponseDTO;
import com.aat_projects.code_review.service.AnalysisQueryService;
import com.aat_projects.code_review.service.JavaAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CodeAnalysisController {
    private final JavaAnalysisService service;
    private final AnalysisQueryService queryService;
    @PostMapping(value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseDTO> upload(
            @RequestPart("data") CARequestDTO requestDTO,
            @RequestPart("file")MultipartFile file
            ) throws IOException  {
        return ResponseEntity.ok(service.parseJava(file));
    }

    @PostMapping("/upload1")
    public ResponseEntity<CAResponseDTO> upload(
            @RequestBody CARequestDTO1 requestDTO
    ){
        return ResponseEntity.ok(service.parseJava(requestDTO.getSourcecode()));
    }

    @GetMapping("/{analysisRunId}")
    public ResponseDTO getAnalysis(@PathVariable Long analysisRunId){
        return queryService.fetchAnalysis(analysisRunId);
    }

}
