package br.com.vote.no.restaurante.support;

import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;
import br.com.vote.no.restaurante.model.Voting;
import br.com.vote.no.restaurante.resource.param.RankingResponse;
import br.com.vote.no.restaurante.resource.param.RestaurantResponse;
import br.com.vote.no.restaurante.resource.param.UserRequest;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vinicius on 24/12/15.
 */
public class TestApiEndpoints {

    private static final String VOTING_ENDPOINT = "http://127.0.0.1:8081/v1/votings";
    private static final String RESTAURANT_ENDPOINT = "http://127.0.0.1:8081/v1/restaurants";
    private static final String USER_ENDPOINT = "http://127.0.0.1:8081/v1/users";
    private static final String RANKING_ENDPOINT = "http://127.0.0.1:8081/v1/rankings";

    public static ResponseEntity<Voting> beginVoting() {
        return new TestRestTemplate().getForEntity(VOTING_ENDPOINT, Voting.class);
    }

    public static ResponseEntity<Voting> voting(final Long restaurantId) {
        return new TestRestTemplate().getForEntity(VOTING_ENDPOINT + "/" + restaurantId, Voting.class);
    }

    public static ResponseEntity<RestaurantResponse> findAllRestaurants() {
        return new TestRestTemplate().getForEntity(RESTAURANT_ENDPOINT, RestaurantResponse.class);
    }

    public static ResponseEntity<User> createUser(final UserRequest user) {
        return new TestRestTemplate().postForEntity(USER_ENDPOINT, user, User.class, Collections.EMPTY_MAP);
    }

    public static ResponseEntity<User> findUserByEmail(final String email) {
        return new TestRestTemplate().getForEntity(USER_ENDPOINT + "/" + email, User.class);
    }

    public static ResponseEntity<RankingResponse> getRankingByUser(final Long userId) {
        return new TestRestTemplate().getForEntity(RANKING_ENDPOINT + "/" + userId, RankingResponse.class);
    }

    public static ResponseEntity<RankingResponse> getGeneralRanking() {
        return new TestRestTemplate().getForEntity(RANKING_ENDPOINT, RankingResponse.class);
    }

}


