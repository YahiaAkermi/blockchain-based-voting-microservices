package com.yahia.votingms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CastedVoteRejectedException extends RuntimeException {

    public CastedVoteRejectedException(String msg){
        super(msg);
    }
}
