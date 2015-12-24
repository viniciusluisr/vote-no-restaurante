package br.com.vote.no.restaurante.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.vote.no.restaurante.model.Ranking;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;
import br.com.vote.no.restaurante.repository.RankingRepository;
import br.com.vote.no.restaurante.repository.UserRepository;
import br.com.vote.no.restaurante.repository.VoteRepository;
import br.com.vote.no.restaurante.service.provider.UserServiceProvider;
import br.com.vote.no.restaurante.support.TestFixtureSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Vinicius on 22/12/15.
 */
@RunWith(JUnit4.class)
public class UserServiceTest extends TestFixtureSupport {

    @Mock
    private UserRepository userRepository;
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private RankingRepository rankingRepository;
    @InjectMocks
    private UserService userService = new UserServiceProvider();
    private Optional<User> expected;
    private List<Vote> expectedVotes;
    private Optional<Restaurant> expectedRestaurant;
    private Optional<Ranking> expectedRanking;

    @Override
    public void setUp() {
        expected = getExpectedUser();
        expectedVotes = getExpectedVotes();
        expectedRestaurant = getExpectedRestaurant();
        expectedRanking = getExpectedRanking();
        when(voteRepository.save(any(Vote.class))).thenReturn(expectedVotes.get(0));
    }

    @Test
    public void testCreateUser() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(expected.get());
        testRefreshRanking();
        Optional<User> saved = userService.createUser(getExpectedUserWithoutId().get(), expectedVotes);
        assertThat(expected, equalTo(saved));
    }

    @Test
    public void testCreateUserExisting() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(expected.get());
        testRefreshRanking();
        Optional<User> saved = userService.createUser(getExpectedUserWithoutId().get(), expectedVotes);
        assertThat(expected, equalTo(saved));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateUserWithInvalidFields() {
        User user = new User();
        when(userRepository.findByEmail(any(String.class))).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(expected.get());
        testRefreshRanking();
        Optional<User> saved = userService.createUser(user, expectedVotes);
        assertThat(expected, equalTo(user));
    }

    @Test
    public void testRefreshRanking() {
        when(restaurantService.findRestaurantById(any(Long.class))).thenReturn(expectedRestaurant);
        when(rankingRepository.findByRestaurant(any(Restaurant.class))).thenReturn(expectedRanking);
    }


    private Optional<User> getExpectedUser() {
        return Optional.of(Fixture.from(User.class).gimme("valid"));
    }

    private Optional<User> getExpectedUserWithoutId() {
        return Optional.of(Fixture.from(User.class).gimme("validWithoutId"));
    }

    private List<Vote> getExpectedVotes() {
        return Fixture.from(Vote.class).gimme(2,"valid");
    }

    private Optional<Restaurant> getExpectedRestaurant() {
        return Optional.of(Fixture.from(Restaurant.class).gimme("valid"));
    }

    private Optional<Ranking> getExpectedRanking() {
        return  Optional.of(Fixture.from(Ranking.class).gimme("valid"));
    }

}
