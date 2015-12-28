package br.com.vote.no.restaurante.repository;

import br.com.vote.no.restaurante.model.Ranking;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Vinicius on 24/12/15.
 */
@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {

    Optional<Ranking> findByRestaurant(final Restaurant restaurant);
    Optional<Ranking> findByRestaurantAndUser(final Restaurant restaurant, final User user);

}
