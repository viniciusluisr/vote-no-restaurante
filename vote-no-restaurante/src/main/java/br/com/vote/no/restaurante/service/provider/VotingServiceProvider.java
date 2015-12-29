package br.com.vote.no.restaurante.service.provider;

import br.com.vote.no.restaurante.exception.RestaurantNotFoundException;
import br.com.vote.no.restaurante.exception.VotingDidNotBeginException;
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
    private LinkedList<Restaurant> preferences;
    private Restaurant first;
    private Restaurant second;

    @Override
    public Optional<Voting> beginVoting() {
        log.info("Iniciando uma nova votação");

        restaurants = new LinkedList<>();
        preferences = new LinkedList<>();
        restaurants.addAll(restaurantService.findAll());
        getNextVoting();

        return Optional.of(new Voting(first, second));
    }

    @Override
    public Optional<Voting> voting(final Long restaurantId) {
        Preconditions.checkNotNull(restaurantId);

        if (restaurants == null)
            throw new VotingDidNotBeginException("A votação não foi iniciada, por favor, inicie a votação usando o resource /votings");

        Optional<Restaurant> restaurant = restaurantService.findRestaurantById(restaurantId);

        if(restaurant == null)
            throw  new RestaurantNotFoundException("Restaurante não encontrado, por favor, verifique o Id informado");

        log.info("Realizado um novo voto para o restaurante: " + restaurant.toString());

        if(restaurant.get().equals(first))
            preparePreference(first, second);
        else
            preparePreference(second, first);

        if(restaurants.size() > preferences.size()){
            return Optional.of(new Voting(first, second));
        }else{
            return Optional.of(new Voting());
        }

    }

    private void preparePreference(final Restaurant selected, final Restaurant discarded) {
        if(selected == null) return;

        if(preferences.isEmpty()) {
            preferences.add(selected);
            preferences.add(discarded);
            getNextVoting();
            return;
        }

        if(selected.equals(first)) {
            Integer posterior = getPosition(selected, true, false);

            if(preferences.size() == posterior){
                preferences.add(discarded);
                getNextVoting();
            }else{
                Restaurant next = preferences.get(posterior);
                first = next;
            }
        }else{
            Integer position = getPosition(discarded, false, false);
            preferences.add(position, selected);
            getNextVoting();
        }
    }

    private Restaurant notVotedRestaurant() {
        for (Restaurant restaurant : restaurants) {
            if(!preferences.contains(restaurant)) return restaurant;
        }
        return null;
    }

    private Integer getPosition(final Restaurant restaurante, final boolean posterior, final boolean previous){
        Integer position = 0;
        for(int i = 0; i < preferences.size(); i++){
            if(restaurante.equals(preferences.get(i))){
                position = i;
                break;
            }
        }

        if(posterior)
            return ++position;
        else if(previous)
            return --position;
        else
            return position;

    }

    private void getNextVoting() {
        if (preferences.isEmpty()) {
            first = restaurants.get(0);
            second = restaurants.get(1);
        } else {
            first = preferences.get(0);
            second = notVotedRestaurant();
        }
    }



}