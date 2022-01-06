package com.github.GabsOda.creditAnalysisAPI.exception;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException(Long id) {
        super("Not found client with Id: " + id);
    }

    public ClientNotFoundException(String message, Long id) {
        super(message+id);
    }

}
