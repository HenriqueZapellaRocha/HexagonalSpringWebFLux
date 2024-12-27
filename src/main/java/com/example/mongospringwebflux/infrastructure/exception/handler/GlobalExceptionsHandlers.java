package com.example.mongospringwebflux.infrastructure.exception.handler;


import com.example.mongospringwebflux.infrastructure.exception.dtos.GlobalExceptionDTO;
import com.example.mongospringwebflux.infrastructure.exception.dtos.InvalidInputValuesExceptionDTO;
import com.example.mongospringwebflux.infrastructure.exception.dtos.NotFoundExceptionDTO;
import com.example.mongospringwebflux.infrastructure.exception.GlobalException;
import com.example.mongospringwebflux.infrastructure.exception.NotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionsHandlers {

    @ResponseStatus( HttpStatus.NOT_FOUND )
    @ResponseBody
    @ExceptionHandler( NotFoundException.class )
    public Mono<NotFoundExceptionDTO> handler( final NotFoundException e ) {
        return Mono.just( new  NotFoundExceptionDTO( e.getMessage() ));
    }

    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( WebExchangeBindException.class )
    public Mono<InvalidInputValuesExceptionDTO> handleValidationException( WebExchangeBindException ex ) {
        List<String> errorMessages = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map( DefaultMessageSourceResolvable::getDefaultMessage )
                .collect(Collectors.toList());

        InvalidInputValuesExceptionDTO errorResponse = new InvalidInputValuesExceptionDTO( errorMessages );
        return Mono.just(errorResponse);
    }

    @ResponseBody
    @ResponseStatus( HttpStatus.FORBIDDEN )
    @ExceptionHandler( AccessDeniedException.class )
    public Mono<GlobalExceptionDTO> handleAccessDenied( AccessDeniedException ex ) {
        return Mono.just( new GlobalExceptionDTO( ex.getMessage() ) );
    }

    @ResponseBody
    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    @ExceptionHandler( BadCredentialsException.class )
    public Mono<GlobalExceptionDTO> handleAccessDenied( BadCredentialsException ex ) {
        return Mono.just( new GlobalExceptionDTO( ex.getMessage() ) );
    }

    @ResponseBody
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( GlobalException.class )
    public Mono<GlobalExceptionDTO> handleAccessDenied( GlobalException ex ) {
        return Mono.just( new GlobalExceptionDTO( ex.getMessage() ) );
    }


}