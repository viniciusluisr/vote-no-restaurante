package br.com.vote.no.restaurante.service;

import br.com.vote.no.restaurante.model.Restaurant;

import java.util.List;
import java.util.Optional;

/**
 * Created by Vinicius on 22/12/15.
 */
public interface RestaurantService {

    Optional<Restaurant> findRestaurantById(final Long id);
    List<Restaurant> findAll();

}
