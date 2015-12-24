package br.com.vote.no.restaurante.service.provider;

import br.com.vote.no.restaurante.model.Ranking;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;
import br.com.vote.no.restaurante.repository.RankingRepository;
import br.com.vote.no.restaurante.repository.VoteRepository;
import br.com.vote.no.restaurante.service.RankingService;
import br.com.vote.no.restaurante.service.RestaurantService;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    private Logger log = Logger.getLogger(RankingServiceProvider.class);

    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Override
    public List<Ranking> getGeneralRanking() {
        log.info("Obtendo o ranking geral de restaurantes");
        return rankingRepository.findAll(new Sort(Sort.Direction.DESC, "points"));
    }

    @Override
    public List<Ranking> getRankingByUser(final User user) {
        Preconditions.checkNotNull(user);

        log.info("Obtendo o ranking do usuário: " + user.toString());

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