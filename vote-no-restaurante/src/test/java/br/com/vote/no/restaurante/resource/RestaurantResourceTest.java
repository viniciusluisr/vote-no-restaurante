package br.com.vote.no.restaurante.resource;

import br.com.vote.no.restaurante.Application;
import br.com.vote.no.restaurante.model.Restaurant;
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

import java.util.List;

/**
 * Created by Vinicius on 24/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest("server.port=8081")
public class RestaurantResourceTest extends TestFixtureSupport {

    @Override
    public void setUp() {

    }

    @Test
    public void testFindAllRestaurants() {
        ResponseEntity<RestaurantResponse> expectedRestaurants = getExpectedRestaurantResponse();
        List<Restaurant> restaurants = expectedRestaurants.getBody().getRestaurants();
        assertThat(5, equalTo(restaurants.size()));
        assertEquals(HttpStatus.OK, expectedRestaurants.getStatusCode());
    }

    private ResponseEntity<RestaurantResponse> getExpectedRestaurantResponse() {
        return TestApiEndpoints.findAllRestaurants();
    }
}
