package br.com.vote.no.restaurante.resource;

import br.com.six2six.fixturefactory.Fixture;
import br.com.vote.no.restaurante.Application;
import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;
import br.com.vote.no.restaurante.resource.param.RankingResponse;
import br.com.vote.no.restaurante.resource.param.RestaurantResponse;
import br.com.vote.no.restaurante.resource.param.UserRequest;
import br.com.vote.no.restaurante.support.TestApiEndpoints;
import br.com.vote.no.restaurante.support.TestFixtureSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Vinicius on 26/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest("server.port=8081")
public class RankingResourceTest extends TestFixtureSupport {

    @Override
    public void setUp() {}

    @Test
    public void testGetRankingByUser() {
        ResponseEntity<RestaurantResponse> restaurantResponse = TestApiEndpoints.findAllRestaurants();
        Vote vote1 = new Vote(restaurantResponse.getBody().getRestaurants().get(0), null);
        Vote vote2 = new Vote(restaurantResponse.getBody().getRestaurants().get(1), null);

        List<Vote> expectedVotes = Arrays.asList(vote1, vote2);

        // Creating user object manually in order to prevent conflicts with the next test method
        User user = new User("vinicius.x@icloud.com", "Vinícius Luis Rodrigues de Souza");
        UserRequest userRequest = new UserRequest(user, expectedVotes);

        ResponseEntity<User> responseEntity = TestApiEndpoints.createUser(userRequest);

        ResponseEntity<RankingResponse> rankingResponse = TestApiEndpoints.getRankingByUser(responseEntity.getBody().getId());
        assertEquals(expectedVotes.size(), rankingResponse.getBody().getRankings().size());
        assertEquals(HttpStatus.OK, rankingResponse.getStatusCode());
    }

    @Test
    public void testGetGeneralRanking() {
        ResponseEntity<RankingResponse> firstResponse = TestApiEndpoints.getGeneralRanking();
        assertEquals(0, firstResponse.getBody().getRankings().size());
        assertEquals(HttpStatus.OK, firstResponse.getStatusCode());

        ResponseEntity<RestaurantResponse> restaurantResponse = TestApiEndpoints.findAllRestaurants();
        Vote vote1 = new Vote(restaurantResponse.getBody().getRestaurants().get(0), null);
        Vote vote2 = new Vote(restaurantResponse.getBody().getRestaurants().get(1), null);

        List<Vote> expectedVotes = Arrays.asList(vote1, vote2);

        UserRequest userRequest = new UserRequest(getExpectedUserWithoutId().get(), expectedVotes);

        ResponseEntity<User> responseEntity = TestApiEndpoints.createUser(userRequest);
        TestApiEndpoints.getRankingByUser(responseEntity.getBody().getId());

        ResponseEntity<RankingResponse> secondResponse = TestApiEndpoints.getGeneralRanking();
        assertEquals(2, secondResponse.getBody().getRankings().size());
        assertEquals(HttpStatus.OK, firstResponse.getStatusCode());

        Vote vote3 = new Vote(restaurantResponse.getBody().getRestaurants().get(2), null);
        Vote vote4 = new Vote(restaurantResponse.getBody().getRestaurants().get(3), null);
        Vote vote5 = new Vote(restaurantResponse.getBody().getRestaurants().get(4), null);

        expectedVotes = Arrays.asList(vote3, vote4, vote5);

        userRequest = new UserRequest(getExpectedUserWithoutId().get(), expectedVotes);

        responseEntity = TestApiEndpoints.createUser(userRequest);
        TestApiEndpoints.getRankingByUser(responseEntity.getBody().getId());

        ResponseEntity<RankingResponse> thirdResponse = TestApiEndpoints.getGeneralRanking();
        assertEquals(5, thirdResponse.getBody().getRankings().size());
        assertEquals(HttpStatus.OK, thirdResponse.getStatusCode());
    }

    private Optional<User> getExpectedUserWithoutId() {
        return Optional.of(Fixture.from(User.class).gimme("validWithoutId"));
    }

}
