package com.github.GabsOda.creditAnalysisAPI.exception;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException(String message, Long id) {
        super(message + id);
    }

}
