package com.teamportfolio.it.exception;

public class TeamPortfolioException extends Exception {

	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public TeamPortfolioException(String errorMessage) {

		super(errorMessage);

		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}