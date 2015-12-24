package br.com.vote.no.restaurante.support;

import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.Voting;
import br.com.vote.no.restaurante.resource.RestaurantResponse;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vinicius on 24/12/15.
 */
public class TestApiEndpoints {

    private static final String VOTING_ENDPOINT = "http://127.0.0.1:8081/v1/votings";
    private static final String RESTAURANT_ENDPOINT = "http://127.0.0.1:8081/v1/restaurants";

    public static ResponseEntity<Voting> beginVoting() {
        return new TestRestTemplate().getForEntity(VOTING_ENDPOINT, Voting.class);
    }

    public static ResponseEntity<RestaurantResponse> findAllRestaurants() {
        return new TestRestTemplate().getForEntity(RESTAURANT_ENDPOINT, RestaurantResponse.class);

    }

}


