package com.aat_projects.code_review.service;

import com.aat_projects.code_review.exception.AnalysisNotFoundException;
import com.aat_projects.code_review.exception.PersistenceException;
import lombok.NoArgsConstructor;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@NoArgsConstructor
public class HashUtil {
    public static String sha256(String input){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for(byte b : hashBytes){
                hex.append(String.format("%02x",b));
            }
            return hex.toString();
        }catch(Exception e){
            throw new PersistenceException("hashcode couldn't be computed", e);
        }
    }
}
