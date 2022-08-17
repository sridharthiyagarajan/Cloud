package com.teamportfolio.it.exception;

public class DataNotFoundException extends TeamPortfolioException {

	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}