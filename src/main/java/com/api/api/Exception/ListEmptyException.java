package com.api.api.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FOUND)
public class ListEmptyException extends Exception{

    public ListEmptyException(){}

    public ListEmptyException(String message){
        super(message);
    }
}
