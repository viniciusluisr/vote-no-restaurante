package br.com.vote.no.restaurante.service.provider;

import br.com.vote.no.restaurante.exception.UserNotFoundException;
import br.com.vote.no.restaurante.model.Ranking;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;
import br.com.vote.no.restaurante.repository.RankingRepository;
import br.com.vote.no.restaurante.repository.UserRepository;
import br.com.vote.no.restaurante.repository.VoteRepository;
import br.com.vote.no.restaurante.service.RankingService;
import br.com.vote.no.restaurante.service.RestaurantService;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
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
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Ranking> getGeneralRanking() {
        log.info("Obtendo o ranking geral de restaurantes");

        Map<Restaurant, List<Ranking>> rankingsByRestaurant = rankingRepository.findAll(new Sort(Sort.Direction.DESC, "points")).stream().collect(Collectors.groupingBy(r -> r.getRestaurant()));
        List<Ranking> rankings = new ArrayList<>();
        for(Restaurant restaurant : rankingsByRestaurant.keySet())   {
            Integer points = 0;
            if(rankingsByRestaurant.get(restaurant).size() > 1) {
                for(Ranking ranking : rankingsByRestaurant.get(restaurant)) {
                    points += ranking.getPoints();
                }
                rankings.add(new Ranking(restaurant, null, points));
            } else {
                rankings.add(rankingsByRestaurant.get(restaurant).get(0));
            }
        }

        rankings.sort(Comparator.comparing(Ranking::getPoints).reversed());
        return rankings;
    }

    @Override
    public List<Ranking> getRankingByUser(final Long userId) {
        Preconditions.checkNotNull(userId);

        User user = userRepository.findOne(userId);

        if(user == null)
            throw  new UserNotFoundException("Usuário não encontrado, por favor, verifique o id informado: " + userId);

        log.info("Obtendo o ranking do usuário: " + user.toString());

        List<Ranking> rankings = new ArrayList<>();
        List<Vote> votes = voteRepository.findByUser(user);
        Map<Long, List<Vote>> votesByRestaurant = votes.stream().collect(Collectors.groupingBy(v -> v.getRestaurant().getId()));

        for(Long id : votesByRestaurant.keySet())   {
            Optional<Ranking> ranking = rankingRepository.findByRestaurantAndUser(restaurantService.findRestaurantById(id).get(), user);
            rankings.add(ranking.get());
        }
        rankings.sort(Comparator.comparing(Ranking::getPoints).reversed());
        return rankings;
    }

}