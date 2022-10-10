package com.example.demo.exception;

public class BadRequestException extends Exception {

    private static final long serialVersionUID = 1126828484956501662L;

	public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
