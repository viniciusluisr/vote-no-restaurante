package br.com.vote.no.restaurante.service.provider;

import br.com.vote.no.restaurante.model.Ranking;
import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;
import br.com.vote.no.restaurante.repository.RankingRepository;
import br.com.vote.no.restaurante.repository.UserRepository;
import br.com.vote.no.restaurante.repository.VoteRepository;
import br.com.vote.no.restaurante.service.RestaurantService;
import br.com.vote.no.restaurante.service.UserService;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Vinicius on 22/12/15.
 */
@Service
public class UserServiceProvider implements UserService {

    private Logger log = Logger.getLogger(UserServiceProvider.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Override
    public Optional<User> createUser(final User user, final List<Vote> votes) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(user.getEmail());
        Preconditions.checkNotNull(user.getName());
        Preconditions.checkNotNull(votes);

        log.info("Cadastrando um novo usuário: " + user.toString() + " com os seguintes votos: " + votes.toString());

        final User found = userRepository.findByEmail(user.getEmail());

        votes.forEach(vote -> {
            if(found != null)
                vote.setUser(found);
            else
                vote.setUser(user);
            voteRepository.save(vote);
        });

        refreshRanking(votes, found != null ? found : user);

        return found != null ? Optional.of(found) : Optional.of(user);
    }

    private void refreshRanking(final List<Vote> votes, final User user) {
        Map<Long, List<Vote>> votesByRestaurant = votes.stream().collect(Collectors.groupingBy(v -> v.getRestaurant().getId()));

        for(Long id : votesByRestaurant.keySet())   {
            Optional<Ranking> ranking = rankingRepository.findByRestaurantAndUser(restaurantService.findRestaurantById(id).get(), user);
            Integer points = null;
            if(ranking.isPresent()) {
                points = ranking.get().getPoints();
                points += votesByRestaurant.get(id).size();
                ranking.get().setPoints(points);
                rankingRepository.save(ranking.get());
            } else {
                points =+ votesByRestaurant.get(id).size();
                Ranking newRanking = new Ranking(restaurantService.findRestaurantById(id).get(), user, points);
                rankingRepository.save(newRanking);
            }
        }
    }

}