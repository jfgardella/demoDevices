package com.example.demo.exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = -3071795321131275855L;

	public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
