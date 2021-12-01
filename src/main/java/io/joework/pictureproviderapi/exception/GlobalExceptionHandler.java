package io.joework.pictureproviderapi.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.joework.pictureproviderapi.domain.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
   public ResponseEntity<?> handleAllUnCaughtExection(Exception e, WebRequest request){
       log.info("handler of all generic exception is executing...");
       return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                 String.format("Interal Server error %s",e.getLocalizedMessage())));

   }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
                
        log.info("a MethodArgumentNotFound is caught in the request path {}", request);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "the method arguments are not valid");

        ex.getBindingResult().getFieldErrors().forEach(error -> errorResponse.addValidationError(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(DataAccessException.class)
    // @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleViolationException(DataAccessException e, WebRequest request){
        log.info("DataAccessException is caughted in the request path {}", request);
        
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST
        , "this request cause a violation of a defined integrity constraint and the reason is " + e.getMostSpecificCause()  );

        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialException(BadCredentialsException e, WebRequest request){
        log.info("Bad Cred is occured... ");

        ErrorResponse er = new ErrorResponse(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(er);
        }
}
