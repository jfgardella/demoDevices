package com.example.demo.exception;

public class ConflictException extends Exception {

    private static final long serialVersionUID = -2548063417810875680L;

	public ConflictException() {
        super();
    }

    public ConflictException(String message){
        super(message);
    }
}
