package com.tekpyrmid.moviee.exception;

public class DuplicateRecordException extends RuntimeException{

    public DuplicateRecordException(String message){
        super(message);
    }

    public DuplicateRecordException(){
        super("Record already exist");
    }

}
