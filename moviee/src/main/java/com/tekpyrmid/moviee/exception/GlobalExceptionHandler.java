package com.tekpyrmid.moviee.exception;

import com.tekpyrmid.moviee.response.FailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<FailureResponse> duplicateRecordHandler(DuplicateRecordException exception){
        FailureResponse failureResponse=new FailureResponse();
        failureResponse.setData(null);
        failureResponse.setError(true);
        failureResponse.setHttpStatus(HttpStatus.CONFLICT);
        failureResponse.setMessage(exception.getMessage());

        return ResponseEntity.status(failureResponse.getHttpStatus()).body(failureResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : exception.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return errors;
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<FailureResponse>generalExceptionHandler(RuntimeException exception){
        FailureResponse failureResponse=new FailureResponse();
        failureResponse.setData(null);
        failureResponse.setError(true);
        failureResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        failureResponse.setMessage(exception.getMessage());

        return ResponseEntity.status(failureResponse.getHttpStatus()).body(failureResponse);
    }
}
