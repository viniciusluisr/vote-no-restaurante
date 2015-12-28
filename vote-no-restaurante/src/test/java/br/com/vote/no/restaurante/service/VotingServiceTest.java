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
    private List<Restaurant> expectedRestaurants;
    private Optional<Voting> voting;
    private Restaurant first;
    private Restaurant second;
    private List<Restaurant> preferences;

    @Override
    public void setUp() {
        expecteds = getExpectedRestaurants();
        expected = getExpectedRestaurant();
        when(restaurantService.findAll()).thenReturn(expecteds);
    }

    @Test
    public void testBeginVoting() {
        voting = votingService.beginVoting();
        preferences = new ArrayList<>();
        expectedRestaurants = new ArrayList<>();
        expectedRestaurants.addAll(expecteds);
        getNextVoting();
        Voting expectedVoting = new Voting(first, second);
        assertThat(Optional.of(expectedVoting), equalTo(voting));
    }

    @Test
    public void testVoting() {
        testBeginVoting();
        when(restaurantService.findRestaurantById(any(Long.class))).thenReturn(Optional.of(first));
        voting = votingService.voting(first.getId());

        preparePreference(first, second);

        Voting expectedVoting = new Voting(first, second);
        assertThat(Optional.of(expectedVoting), equalTo(voting));

        when(restaurantService.findRestaurantById(any(Long.class))).thenReturn(Optional.of(second));
        voting = votingService.voting(second.getId());
        preparePreference(second, first);

        expectedVoting = new Voting(first, second);
        assertThat(Optional.of(expectedVoting), equalTo(voting));
    }

    @Test
    public void testVotingNoMoreLines() {
        testBeginVoting();
        when(restaurantService.findRestaurantById(any(Long.class))).thenReturn(Optional.of(first));
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

    private void preparePreference(Restaurant selected, Restaurant discarded) {
        if(selected == null) return;

        if(preferences.isEmpty()) {
            preferences.add(selected);
            preferences.add(discarded);
            getNextVoting();
            return;
        }

        if(selected.equals(first)) {
            Integer posterior = getPosition(selected, true, false);

            if(preferences.size() == posterior){
                preferences.add(discarded);
                getNextVoting();
            }else{
                Restaurant next = preferences.get(posterior);
                first = next;
            }
        }else{
            Integer position = getPosition(discarded, false, false);
            preferences.add(position, selected);
            getNextVoting();
        }
    }

    private Restaurant notVotedRestaurant() {
        for (Restaurant restaurant : expectedRestaurants) {
            if(!preferences.contains(restaurant)) return restaurant;
        }
        return null;
    }

    private Integer getPosition(Restaurant restaurante, boolean posterior, boolean previous){
        Integer position = 0;
        for(int i = 0; i < preferences.size(); i++){
            if(restaurante.equals(preferences.get(i))){
                position = i;
                break;
            }
        }

        if(posterior)
            return ++position;
        else if(previous)
            return --position;
        else
            return position;

    }

    private void getNextVoting() {
        if (preferences.isEmpty()) {
            first = expectedRestaurants.get(0);
            second = expectedRestaurants.get(1);
        } else {
            first = preferences.get(0);
            second = notVotedRestaurant();
        }
    }

}