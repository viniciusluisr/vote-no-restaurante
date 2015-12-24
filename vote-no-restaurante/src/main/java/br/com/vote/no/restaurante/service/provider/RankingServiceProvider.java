package br.com.vote.no.restaurante.service.provider;

import br.com.vote.no.restaurante.model.Ranking;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;
import br.com.vote.no.restaurante.repository.RankingRepository;
import br.com.vote.no.restaurante.repository.VoteRepository;
import br.com.vote.no.restaurante.service.RankingService;
import br.com.vote.no.restaurante.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Vinicius on 24/12/15.
 */
@Service
public class RankingServiceProvider implements RankingService {

    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Override
    public List<Ranking> getGeneralRanking() {
        return rankingRepository.findAllByOrderByPoints();
    }

    @Override
    public List<Ranking> getRankingByUser(final User user) {
        List<Ranking> rankings = null;
        List<Vote> votes = voteRepository.findByUser(user);
        Map<Long, List<Vote>> votesByRestaurant = votes.stream().collect(Collectors.groupingBy(v -> v.getRestaurant().getId()));

        for(Long id : votesByRestaurant.keySet())   {
            Optional<Ranking> ranking = rankingRepository.findByRestaurant(restaurantService.findRestaurantById(id).get());
            ranking.ifPresent( r -> {
                Integer points = r.getPoints();
                points += votesByRestaurant.get(id).size();
                r.setPoints(points);
                rankings.add(r);
            });
        }

        return rankings;
    }

}