package com.yahia.vmms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DateTimeIncohrentException extends RuntimeException{
    public DateTimeIncohrentException(String msg){
        super(msg);
    }
}
