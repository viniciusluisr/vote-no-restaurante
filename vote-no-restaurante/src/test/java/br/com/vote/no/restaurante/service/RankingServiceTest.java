package br.com.vote.no.restaurante.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.vote.no.restaurante.exception.UserNotFoundException;
import br.com.vote.no.restaurante.model.Ranking;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;
import br.com.vote.no.restaurante.repository.RankingRepository;
import br.com.vote.no.restaurante.repository.UserRepository;
import br.com.vote.no.restaurante.repository.VoteRepository;
import br.com.vote.no.restaurante.service.provider.RankingServiceProvider;
import br.com.vote.no.restaurante.support.TestFixtureSupport;
import com.google.common.base.Preconditions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;

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
    @Mock
    private UserRepository userRepository;
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
        when(rankingRepository.findAll(new Sort(Sort.Direction.DESC, "points"))).thenReturn(expectedRankings);
        List<Ranking> rankings = rankingService.getGeneralRanking();

        Map<Restaurant, List<Ranking>> rankingsByRestaurant = expectedRankings.stream().collect(Collectors.groupingBy(r -> r.getRestaurant()));
        List<Ranking> list = new ArrayList<>();
        for(Restaurant restaurant : rankingsByRestaurant.keySet())   {
            Integer points = 0;
            if(rankingsByRestaurant.get(restaurant).size() > 1) {
                for(Ranking ranking : rankingsByRestaurant.get(restaurant)) {
                    points += ranking.getPoints();
                }
                list.add(new Ranking(restaurant, null, points));
            } else {
                list.add(rankingsByRestaurant.get(restaurant).get(0));
            }
        }
        list.sort(Comparator.comparing(Ranking::getPoints).reversed());
        assertThat(list, equalTo(rankings));
    }

    @Test
    public void testGetRankingByUser() {
        when(userRepository.findOne(any(Long.class))).thenReturn(expectedUser.get());
        when(voteRepository.findByUser(any(User.class))).thenReturn(expectedVotes);
        when(restaurantService.findRestaurantById(any(Long.class))).thenReturn(expectedRestaurant);
        when(rankingRepository.findByRestaurantAndUser(any(Restaurant.class), any(User.class))).thenReturn(expectedRanking);
        List<Ranking> rankings = rankingService.getRankingByUser(expectedUser.get().getId());

        List<Ranking> list = new ArrayList<>();
        Map<Long, List<Vote>> votesByRestaurant = expectedVotes.stream().collect(Collectors.groupingBy(v -> v.getRestaurant().getId()));
        for(Long id : votesByRestaurant.keySet())   {
            Optional<Ranking> ranking = rankingRepository.findByRestaurantAndUser(restaurantService.findRestaurantById(id).get(), expectedUser.get());
            list.add(ranking.get());
         }
        rankings.sort(Comparator.comparing(Ranking::getPoints).reversed());
        assertThat(list, equalTo(rankings));
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetRankingByUserWithNoExistingUser() {
        when(voteRepository.findByUser(any(User.class))).thenReturn(expectedVotes);
        when(restaurantService.findRestaurantById(any(Long.class))).thenReturn(expectedRestaurant);
        when(rankingRepository.findByRestaurant(any(Restaurant.class))).thenReturn(expectedRanking);
        List<Ranking> rankings = rankingService.getRankingByUser(expectedUser.get().getId());
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
