package br.com.vote.no.restaurante.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.vote.no.restaurante.exception.RestaurantNotFoundException;
import br.com.vote.no.restaurante.exception.VotingDidNotBeginException;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.Voting;
import br.com.vote.no.restaurante.repository.RestaurantRepository;
import br.com.vote.no.restaurante.service.provider.VotingServiceProvider;
import br.com.vote.no.restaurante.support.TestApiEndpoints;
import br.com.vote.no.restaurante.support.TestFixtureSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

/**
 * Created by Vinicius on 22/12/15.
 */
@RunWith(JUnit4.class)
public class VotingServiceTest extends TestFixtureSupport {

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private RestaurantService restaurantService;
    @InjectMocks
    private VotingService votingService = new VotingServiceProvider();
    private List<Restaurant> expecteds;
    private Optional<Restaurant> expected;
    private LinkedList<Restaurant> expectedRestaurants;
    private Optional<Voting> voting;

    @Override
    public void setUp() {
        expecteds = getExpectedRestaurants();
        expected = getExpectedRestaurant();
        when(restaurantService.findAll()).thenReturn(expecteds);
    }

    @Test
    public void testBeginVoting() {
        voting = votingService.beginVoting();

        expectedRestaurants = new LinkedList<>();
        expectedRestaurants.addAll(expecteds);

        Voting expectedVoting = new Voting(expectedRestaurants.poll(), expectedRestaurants.poll());
        assertThat(Optional.of(expectedVoting), equalTo(voting));
    }

    @Test
    public void testVoting() {
        when(restaurantService.findRestaurantById(any(Long.class))).thenReturn(expected);
        testBeginVoting();
        voting = votingService.voting(1l);

        Voting expectedVoting = new Voting(expected.get(), expectedRestaurants.poll());
        assertThat(Optional.of(expectedVoting), equalTo(voting));

        voting = votingService.voting(1l);
        expectedVoting = new Voting(expected.get(), expectedRestaurants.poll());
        assertThat(Optional.of(expectedVoting), equalTo(voting));
    }

    @Test
    public void testVotingNoMoreLines() {
        when(restaurantService.findRestaurantById(any(Long.class))).thenReturn(expected);
        testBeginVoting();
        Voting expectedVoting = new Voting();

        while(voting.get().getFirst() != null)
            voting = votingService.voting(1l);

        assertThat(expectedVoting, equalTo(voting.get()));
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testVotingWithNoExistingId() {
        testBeginVoting();
        voting = votingService.voting(201l);
    }

    @Test(expected = VotingDidNotBeginException.class)
    public void testVotingWithoutVotingStarted() {
        voting = votingService.voting(1l);
    }

    private List<Restaurant> getExpectedRestaurants() {
        return Fixture.from(Restaurant.class).gimme(5,"valid");
    }

    private Optional<Restaurant> getExpectedRestaurant() {
        return Optional.of(Fixture.from(Restaurant.class).gimme("valid"));
    }

}


