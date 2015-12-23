package br.com.vote.no.restaurante.service.provider;

import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.Voting;
import br.com.vote.no.restaurante.service.RestaurantService;
import br.com.vote.no.restaurante.service.VotingService;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Vinicius on 22/12/15.
 */
@Service
public class VotingServiceProvider implements VotingService {

    private Logger log = Logger.getLogger(VotingServiceProvider.class);

    @Autowired
    private RestaurantService restaurantService;
    private LinkedList<Restaurant> restaurants;

    @Override
    public Optional<Voting> beginVoting() {
        restaurants = new LinkedList<>();
        restaurants.addAll(restaurantService.findAll());
        return Optional.of(new Voting(restaurants.poll(), restaurants.poll()));
    }

    @Override
    public Optional<Voting> voting(final Long restaurantId) {
        Preconditions.checkNotNull(restaurantId);

        if(restaurants.isEmpty())
            return Optional.of(new Voting());

        Optional<Restaurant> restaurant = restaurantService.findRestaurantById(restaurantId);

        Restaurant second = new Restaurant();
        if(restaurants.peek().equals(restaurant.get())) {
            restaurants.remove();
            second = restaurants.poll();
        } else
            second = restaurants.poll();

        return Optional.of(new Voting(restaurant.get(), second));
    }



}