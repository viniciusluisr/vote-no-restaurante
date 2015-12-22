package br.com.vote.no.restaurante.aspect;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Created by Vinicius on 21/12/15.
 */
@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Message> handlerValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        Message message = new Message(MessageType.Parameter_Error, "Erro de validação");
        for (FieldError fieldError : result.getFieldErrors()) {
            message.addNotification(fieldError.getDefaultMessage());
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Message> handlerValidationException(ConstraintViolationException ex) {
        Message message = new Message(MessageType.Parameter_Error, "Erro de validação");
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            message.addNotification(violation.getMessage());
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.BAD_REQUEST);
    }

}
