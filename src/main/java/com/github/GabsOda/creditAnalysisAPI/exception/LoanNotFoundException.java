package com.github.GabsOda.creditAnalysisAPI.exception;

public class LoanNotFoundException extends RuntimeException{

    public LoanNotFoundException(String message, Long id) {
        super(message + id);
    }

}
