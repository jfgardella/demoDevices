package com.example.demo.exception;

public class InternalServerException extends Exception {

	private static final long serialVersionUID = 7204715914941560446L;

	public InternalServerException() {
        super();
    }

    public InternalServerException(String message) {
        super(message);
    }
}
