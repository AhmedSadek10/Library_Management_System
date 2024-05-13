package com.example.Library.Exceptions;

public class BookNotFound extends RuntimeException{
    public BookNotFound(String message){
        super(message);
    }
}
