package com.yahia.vmms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class VotingSessionAlreadyExists extends RuntimeException{

    public VotingSessionAlreadyExists(String msg){
        super(msg);
    }
}
