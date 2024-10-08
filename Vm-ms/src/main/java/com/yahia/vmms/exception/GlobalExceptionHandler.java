package com.yahia.vmms.exception;

import com.yahia.vmms.dto.responseStructureDTOs.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        //here i initialized HashMap to store error in pairs (fieldName,validationMsg(which i provided on top of each column))
        Map<String, String> validationErrors = new HashMap<>();

        //then i'm trying to fetch all errors and put it in validationErrorList
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        //here i'm iterating validationErrorList and extracting the fieldName and the related validationMsg
        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();

            //then finally i'm putting them in the hashMap
            validationErrors.put(fieldName, validationMsg);
        });

        //finally i'm returning ResponseEntity with HttpStatus.bad_request and the hashMap that contains each field and it's related message error
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,WebRequest webRequest){

        ErrorResponseDto errorResponseDto=new ErrorResponseDto(webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto,HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ErrorResponseDto> handleResourceAlreadyExistsException(ResourceAlreadyExists exception, WebRequest webRequest){

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

    @ExceptionHandler(BelowAgeOfConscentException.class)
    public ResponseEntity<ErrorResponseDto> handleBelowAgeOfConscentException(BelowAgeOfConscentException exception,WebRequest request){
        ErrorResponseDto errorResponseDto=new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDto,HttpStatus.NOT_FOUND);
    }
}
