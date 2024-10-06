package com.yahia.vmms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceAlreadyExists extends RuntimeException{

    public ResourceAlreadyExists(String resourceName,String fieldName){
        super(String.format(" %s already exists with the given %s",resourceName,fieldName));
    }
}
