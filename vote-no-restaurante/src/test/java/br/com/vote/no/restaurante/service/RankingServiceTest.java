package br.com.vote.no.restaurante.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.vote.no.restaurante.model.Ranking;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;
import br.com.vote.no.restaurante.repository.RankingRepository;
import br.com.vote.no.restaurante.repository.VoteRepository;
import br.com.vote.no.restaurante.service.provider.RankingServiceProvider;
import br.com.vote.no.restaurante.support.TestFixtureSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

/**
 * Created by Vinicius on 24/12/15.
 */
@RunWith(JUnit4.class)
public class RankingServiceTest extends TestFixtureSupport {

    @Mock
    private RankingRepository rankingRepository;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private VoteRepository voteRepository;
    @InjectMocks
    private RankingService rankingService = new RankingServiceProvider();
    private List<Ranking> expectedRankings;
    private Optional<Restaurant> expectedRestaurant;
    private Optional<Ranking> expectedRanking;
    private List<Vote> expectedVotes;
    private Optional<User> expectedUser;

    @Override
    public void setUp() {
        expectedRankings = getExpectedRankings();
        expectedVotes = getExpectedVotes();
        expectedRestaurant = getExpectedRestaurant();
        expectedRanking = getExpectedRanking();
        expectedUser = getExpectedUser();
    }

    @Test
    public void testGetGeneralRanking() {
        when(rankingRepository.findAllByOrderByPoints()).thenReturn(expectedRankings);
        List<Ranking> rankings = rankingService.getGeneralRanking();
        assertThat(expectedRankings, equalTo(rankings));
    }

    public void testGetRankingByUser() {
        when(voteRepository.findByUser(any(User.class))).thenReturn(expectedVotes);
        when(restaurantService.findRestaurantById(any(Long.class))).thenReturn(expectedRestaurant);
        when(rankingRepository.findByRestaurant(any(Restaurant.class))).thenReturn(expectedRanking);
        List<Ranking> rankings = rankingService.getRankingByUser(expectedUser.get());

        assertThat(expectedRankings, equalTo(rankings));
    }

    private Optional<Ranking> getExpectedRanking() {
        return  Optional.of(Fixture.from(Ranking.class).gimme("valid"));
    }

    private List<Ranking> getExpectedRankings() {
        return Fixture.from(Ranking.class).gimme(5,"valid");
    }

    private Optional<Restaurant> getExpectedRestaurant() {
        return Optional.of(Fixture.from(Restaurant.class).gimme("valid"));
    }

    private List<Vote> getExpectedVotes() {
        return Fixture.from(Vote.class).gimme(2,"valid");
    }

    private Optional<User> getExpectedUser() {
        return Optional.of(Fixture.from(User.class).gimme("valid"));
    }

}
