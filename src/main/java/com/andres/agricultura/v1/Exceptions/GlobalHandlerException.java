package com.andres.agricultura.v1.Exceptions;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalHandlerException {

    private static final Logger LOGGER = LogManager.getLogger(GlobalHandlerException.class);


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequest (BadRequestException exception, WebRequest request){
        exception.printStackTrace();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BadRequestException",
                exception.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        LOGGER.error(errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> notFound (NotFoundException exception, WebRequest request){
        exception.printStackTrace();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "NotFoundException",
                exception.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        LOGGER.error(errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> illegalArgument (IllegalArgumentException exception, WebRequest request){
        exception.printStackTrace();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "IllegalArgumentException",
                exception.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        LOGGER.error(errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ErrorResponse> arithmetic (ArithmeticException exception, WebRequest request){
        exception.printStackTrace();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "ArithmeticException",
                exception.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        LOGGER.error(errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> nullPointer (NullPointerException exception, WebRequest request){
        exception.printStackTrace();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "NullPointerException",
                exception.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        LOGGER.error(errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
