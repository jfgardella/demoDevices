package com.example.demo.exception;

import org.slf4j.MDC;

import java.util.Date;

public class CustomError {

	private String error;
	private String message;
	private Date date;
	private String operationId;

	public CustomError(String error, String message) {
		this.error = error;
		this.message = message;
		this.operationId = MDC.get("OPERATION_ID");
		this.date = new Date();
	}

	@Override
	public String toString() {
		return "Error: {" + "'error': '" + getError() + "'" + "'message': '" + getMessage() + "'" + "'date': '"
				+ getDate().toString() + "'}";
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	public Date getDate() {
		return date;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

}
