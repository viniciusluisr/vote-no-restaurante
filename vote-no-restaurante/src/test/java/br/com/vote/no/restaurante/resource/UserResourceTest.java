package br.com.vote.no.restaurante.resource;

import br.com.six2six.fixturefactory.Fixture;
import br.com.vote.no.restaurante.Application;
import br.com.vote.no.restaurante.Startup;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;
import br.com.vote.no.restaurante.resource.param.RankingResponse;
import br.com.vote.no.restaurante.resource.param.RestaurantResponse;
import br.com.vote.no.restaurante.resource.param.UserRequest;
import br.com.vote.no.restaurante.support.TestApiEndpoints;
import br.com.vote.no.restaurante.support.TestFixtureSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by Vinicius on 25/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest("server.port=8081")
public class UserResourceTest extends TestFixtureSupport {

    @Override
    public void setUp() {}

    @Test
    public void testCreateUser() {
        ResponseEntity<RestaurantResponse> restaurantResponse = TestApiEndpoints.findAllRestaurants();
        Vote vote1 = new Vote(restaurantResponse.getBody().getRestaurants().get(0), null);
        Vote vote2 = new Vote(restaurantResponse.getBody().getRestaurants().get(1), null);

        List<Vote> expectedVotes = Arrays.asList(vote1, vote2);

        UserRequest userRequest = new UserRequest(getExpectedUserWithoutId().get(), expectedVotes);

        ResponseEntity<User> responseEntity = TestApiEndpoints.createUser(userRequest);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateUserWithInvalidFields() {
        ResponseEntity<RestaurantResponse> restaurantResponse = TestApiEndpoints.findAllRestaurants();
        Vote vote1 = new Vote(restaurantResponse.getBody().getRestaurants().get(0), null);
        Vote vote2 = new Vote(restaurantResponse.getBody().getRestaurants().get(1), null);

        List<Vote> expectedVotes = Arrays.asList(vote1, vote2);

        User user = getExpectedUserWithoutId().get();
        user.setEmail(null);

        UserRequest userRequest = new UserRequest(user, expectedVotes);

        ResponseEntity<User> responseEntity = TestApiEndpoints.createUser(userRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void testGetRankingByUserWithNoExistingRestaurants() {
        Vote vote1 = new Vote(getExpectedRestaurants().get(0), null);
        Vote vote2 = new Vote(getExpectedRestaurants().get(1), null);

        List<Vote> expectedVotes = Arrays.asList(vote1, vote2);

        UserRequest userRequest = new UserRequest(getExpectedUserWithoutId().get(), expectedVotes);

        ResponseEntity<User> responseEntity = TestApiEndpoints.createUser(userRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void testFindUserByEmail() {
        ResponseEntity<RestaurantResponse> restaurantResponse = TestApiEndpoints.findAllRestaurants();
        Vote vote1 = new Vote(restaurantResponse.getBody().getRestaurants().get(0), null);
        Vote vote2 = new Vote(restaurantResponse.getBody().getRestaurants().get(1), null);

        List<Vote> expectedVotes = Arrays.asList(vote1, vote2);

        UserRequest userRequest = new UserRequest(getExpectedUserWithoutId().get(), expectedVotes);

        ResponseEntity<User> expected = TestApiEndpoints.createUser(userRequest);

        ResponseEntity<User> responseEntity = TestApiEndpoints.findUserByEmail(expected.getBody().getEmail());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(expected.getBody(), equalTo(responseEntity.getBody()));
    }

    @Test
    public void testFindUserByEmailWithNoExistingEmail() {
        ResponseEntity<User> responseEntity = TestApiEndpoints.findUserByEmail("fulano@gmail.com");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    private Optional<User> getExpectedUserWithoutId() {
        return Optional.of(Fixture.from(User.class).gimme("validWithoutId"));
    }

    private List<Restaurant> getExpectedRestaurants() {
        return Fixture.from(Restaurant.class).gimme(2,"valid");
    }

}
