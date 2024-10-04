package com.yahia.vmms.exception;

public class RessourceNotFoundException extends RuntimeException{

    public RessourceNotFoundException(String resourceName,String fieldName,String fieldValue){
        super(String.format("%s not found with then given input data %s : %s",resourceName,fieldName,fieldValue));
    }

}
