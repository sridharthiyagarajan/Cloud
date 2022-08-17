package com.teamportfolio.it.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.teamportfolio.it.exception.dto.ErrorDto;
import com.teamportfolio.it.util.Utility;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = { TeamPortfolioException.class })
	public ResponseEntity<ErrorDto> handleTeamPortfolioException(TeamPortfolioException teamPortfolioException) {

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		ErrorDto errorDto = new ErrorDto(Utility.retrieveCurrentTimeStamp(), teamPortfolioException.getErrorMessage(),
				httpStatus.value());

		return new ResponseEntity<>(errorDto, httpStatus);
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException methodArgumentNotValidException) {

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult() != null
				? methodArgumentNotValidException.getBindingResult().getFieldErrors()
				: null;

		String errorMsg = "";

		if (!CollectionUtils.isEmpty(fieldErrors)) {

			for (FieldError fieldError : fieldErrors) {

				StringBuilder sb = new StringBuilder();
				errorMsg = sb.append(errorMsg).append(fieldError.getDefaultMessage()).toString();
			}
		}

		ErrorDto errorDto = new ErrorDto(Utility.retrieveCurrentTimeStamp(), errorMsg, httpStatus.value());
		return new ResponseEntity<>(errorDto, httpStatus);
	}
}