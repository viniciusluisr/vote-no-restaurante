package br.com.vote.no.restaurante.service.provider;

import br.com.vote.no.restaurante.exception.RestaurantNotFoundException;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.repository.RestaurantRepository;
import br.com.vote.no.restaurante.service.RestaurantService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Vinicius on 22/12/15.
 */
@Service
public class RestaurantServiceProvider implements RestaurantService {

    private Logger log = Logger.getLogger(RestaurantServiceProvider.class);

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    
    public Optional<Restaurant> findRestaurantById(final Long id) {
        Preconditions.checkNotNull(id);

        log.info("Buscando um restaurante a partir do Id: " + id);

        Restaurant found = restaurantRepository.findOne(id);

        if(found == null)
            throw  new RestaurantNotFoundException("Restaurante não encontrado, por favor, verifique o Id informado");

        return Optional.of(found);
    }

    @Override
    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        Collections.shuffle(restaurants);
        return restaurants;
    }
}
