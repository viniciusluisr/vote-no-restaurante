package br.com.vote.no.restaurante.repository;

import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Vinicius on 22/12/15.
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
