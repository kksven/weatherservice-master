package com.training.weatherservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Optional;

@RestControllerAdvice
public class WebRestControllerAdvice {

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object>  handleNotFoundException(CustomNotFoundException ex) {
        String weatherInformationNotFound = "Not found exception";
        ResponseMsg responseMsg = new ResponseMsg(new Date(), ex.getLocalizedMessage(), weatherInformationNotFound);

        return new ResponseEntity<>(responseMsg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidDateRequestException.class, MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, IllegalArgumentException.class, ConstraintViolationException.class, MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public ResponseEntity<Object> handleArgumentException(Exception ex) {
        String exceptionMsg = Optional.ofNullable(ex.getMessage()).orElse("");
        String badArgument = "Bad arguments " + exceptionMsg ;
        ResponseMsg responseMsg = new ResponseMsg(new Date(), badArgument, ex.getMessage());

        return new ResponseEntity<>(responseMsg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleException(Exception ex) {
        String internalServerError = "Internal server error";
        ResponseMsg responseMsg = new ResponseMsg(new Date(), internalServerError, ex.getMessage());

        return  new ResponseEntity<>(responseMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
