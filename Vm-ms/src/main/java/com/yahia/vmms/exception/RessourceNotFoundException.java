package com.yahia.vmms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RessourceNotFoundException extends RuntimeException{

    public RessourceNotFoundException(String resourceName,String fieldName,String fieldValue){
        super(String.format("%s not found with then given input data %s : %s",resourceName,fieldName,fieldValue));
    }

}