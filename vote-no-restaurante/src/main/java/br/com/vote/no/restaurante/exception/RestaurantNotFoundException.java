package br.com.vote.no.restaurante.exception;

/**
 * Created by Vinicius on 22/12/15.
 */
public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(final String message) {
        super(message);
    }
}
