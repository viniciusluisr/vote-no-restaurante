package br.com.vote.no.restaurante.resource;

import br.com.vote.no.restaurante.Application;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.Voting;
import br.com.vote.no.restaurante.resource.param.RankingResponse;
import br.com.vote.no.restaurante.resource.param.RestaurantResponse;
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

import java.util.LinkedList;

/**
 * Created by Vinicius on 24/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest("server.port=8081")
public class VotingResourceTest extends TestFixtureSupport {

    @Override
    public void setUp() {}

    @Test
    public void testBeginVoting() {
        assertEquals(HttpStatus.OK, getExpectedVoting().getStatusCode());
   }

    @Test
    public void testVoting() {
        Voting expectedVoting =  getExpectedVoting().getBody();
        ResponseEntity<Voting> voting = TestApiEndpoints.voting(expectedVoting.getFirst().getId());

        LinkedList<Restaurant> restaurants = new LinkedList<>();
        restaurants.addAll(getExpectedRestaurantResponse().getRestaurants());
        restaurants.remove();
        restaurants.remove();
        expectedVoting.setSecond(restaurants.poll());

        assertThat(expectedVoting.getFirst(), equalTo(voting.getBody().getFirst()));
        assertEquals(HttpStatus.OK, voting.getStatusCode());
    }

    @Test
    public void testVotingWithNoExistingRestaurant() {
        testBeginVoting();
        ResponseEntity<Voting> voting = TestApiEndpoints.voting(6l);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, voting.getStatusCode());
    }

    private RestaurantResponse getExpectedRestaurantResponse() {
        return TestApiEndpoints.findAllRestaurants().getBody();
    }

    private ResponseEntity<Voting> getExpectedVoting() {
        return TestApiEndpoints.beginVoting();
    }

    private ResponseEntity<RankingResponse> getRanking() {
        return TestApiEndpoints.getGeneralRanking();
    }

}
