package com.yahia.vmms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BelowAgeOfConscentException extends RuntimeException{
    public BelowAgeOfConscentException(){
        super("you're not eligible to be candidate due to age restrictions");
    }
}
