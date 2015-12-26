package br.com.vote.no.restaurante.exception;

/**
 * Created by Vinicius on 25/12/15.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String message) {
        super(message);
    }
}
