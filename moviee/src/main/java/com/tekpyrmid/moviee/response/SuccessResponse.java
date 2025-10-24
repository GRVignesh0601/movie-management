package com.tekpyrmid.moviee.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SuccessResponse {

    private String message;
    private boolean error;
    private HttpStatus httpStatus;
    private Object Data;
}