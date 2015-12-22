package br.com.vote.no.restaurante.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.vote.no.restaurante.exception.RestaurantNotFoundException;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.repository.RestaurantRepository;
import br.com.vote.no.restaurante.support.TestFixtureSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

/**
 * Created by Vinicius on 22/12/15.
 */
@RunWith(JUnit4.class)
public class RestaurantServiceTest extends TestFixtureSupport {

    @Mock
    private RestaurantRepository restaurantRepository;
    @InjectMocks
    private RestaurantService restaurantService = new RestaurantServiceProvider();
    private Optional<Restaurant> expected;

    @Override
    public void setUp() {
        expected = getExpectedRestaurant();
    }

    @Test
    public void testFindRestaurantById() {
        when(restaurantRepository.findOne(any(Long.class))).thenReturn(expected.get());
        Optional<Restaurant> restaurant = restaurantService.findRestaurantById(expected.get().getId());
        assertThat(expected, equalTo(restaurant));
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testFindRestaurantWithNoExistingId() {
        Optional<Restaurant> restaurant = restaurantService.findRestaurantById(200l);
    }

    @Test
    public void testFindAllRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(getExpectedRestaurants());
        List<Restaurant> restaurants = restaurantService.findAll();
        assertThat(getExpectedRestaurants().size(), equalTo(restaurants.size()));
    }

    private Optional<Restaurant> getExpectedRestaurant() {
        return Optional.of(Fixture.from(Restaurant.class).gimme("valid"));
    }

    private List<Restaurant> getExpectedRestaurants() {
        return Fixture.from(Restaurant.class).gimme(5,"valid");
    }
}
