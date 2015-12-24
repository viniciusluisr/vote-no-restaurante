package br.com.vote.no.restaurante.exception;

/**
 * Created by Vinicius on 24/12/15.
 */
public class VotingDidNotBeginException extends RuntimeException {

    public VotingDidNotBeginException(final String message) {
        super(message);
    }
}
