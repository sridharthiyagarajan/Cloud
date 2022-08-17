package com.teamportfolio.it.exception.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ErrorDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Timestamp time;

	private String errorMessage;

	private int responseCode;

	public ErrorDto() {

	}

	public ErrorDto(Timestamp time, String errorMessage, int responseCode) {

		this.time = time;
		this.errorMessage = errorMessage;
		this.responseCode = responseCode;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
}