package com.teamportfolio.it.exception;

public class InvalidInputException extends TeamPortfolioException {

	private static final long serialVersionUID = 1L;

	public InvalidInputException(String errorMessage) {
		super(errorMessage);
	}

}