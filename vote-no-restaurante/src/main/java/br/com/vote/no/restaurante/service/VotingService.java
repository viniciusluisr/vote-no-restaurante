package br.com.vote.no.restaurante.service;

import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.Voting;

import java.util.Optional;

/**
 * Created by Vinicius on 22/12/15.
 */
public interface VotingService {

    Optional<Voting> beginVoting();
    Optional<Voting> voting(final Long restaurantId);
}
