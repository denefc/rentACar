package com.turkcell.rentACar;


import java.util.HashMap;
import java.util.Map;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.turkcell.rentACar.core.utilities.results.ErrorDataResult;


@SpringBootApplication
@RestControllerAdvice
public class RentACarApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentACarApplication.class, args);
	}
	
	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}

	@ExceptionHandler
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationExceptions(MethodArgumentNotValidException methArgumentNotValidException){
		Map<String,String> validationErrors=new HashMap<>();
		
		for (FieldError fieldError : methArgumentNotValidException.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return new ErrorDataResult<>(validationErrors,"Validation.Error");
	}
	
	@ExceptionHandler
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleBusinessExceptions(BusinessException businessException){
		return new ErrorDataResult<>(businessException.getMessage());
	}
	
}
