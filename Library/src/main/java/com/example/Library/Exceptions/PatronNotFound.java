package com.example.Library.Exceptions;

public class PatronNotFound extends RuntimeException {
    public PatronNotFound(String message){
        super(message);
    }
}
