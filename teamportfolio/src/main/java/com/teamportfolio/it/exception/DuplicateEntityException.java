package com.teamportfolio.it.exception;

public class DuplicateEntityException extends TeamPortfolioException {

	private static final long serialVersionUID = 1L;

	public DuplicateEntityException(String errorMessage) {
		super(errorMessage);
	}

}