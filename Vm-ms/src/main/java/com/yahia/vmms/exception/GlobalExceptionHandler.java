package com.yahia.vmms.exception;

import com.yahia.vmms.dto.responseStructureDTOs.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VotingSessionAlreadyExists.class)
    public ResponseEntity<ErrorResponseDto> handleVotingSessionAlreadyExistsException(VotingSessionAlreadyExists exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto=new
                ErrorResponseDto(webRequest.getDescription(false), HttpStatus.BAD_REQUEST,exception.getMessage()
                , LocalDateTime.now());

        return  new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeIncohrentException.class)
    public ResponseEntity<ErrorResponseDto> handleDateTimeIncohrentException(DateTimeIncohrentException exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto=new
                ErrorResponseDto(webRequest.getDescription(false), HttpStatus.BAD_REQUEST,exception.getMessage()
                , LocalDateTime.now());

        return  new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RessourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(RessourceNotFoundException exception,WebRequest request){
        ErrorResponseDto errorResponseDto=new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDto,HttpStatus.NOT_FOUND);
    }
}
