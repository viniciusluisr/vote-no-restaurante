package br.com.vote.no.restaurante.repository;

import br.com.vote.no.restaurante.model.Ranking;
import br.com.vote.no.restaurante.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Vinicius on 24/12/15.
 */
public interface RankingRepository extends JpaRepository<Ranking, Long> {

    Optional<Ranking> findByRestaurant(final Restaurant restaurant);

    List<Ranking> findAllByOrderByPoints();

}
