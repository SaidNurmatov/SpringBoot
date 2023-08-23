package com.tpe.exeption;

public class ConflictExeption extends RuntimeException{
    public ConflictExeption(String message) {
        super(message);
    }
}
