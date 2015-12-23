package br.com.vote.no.restaurante;

import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.repository.RestaurantRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vinicius on 22/12/15.
 */
@Component
@Scope("singleton")
public class Startup {

    private Logger log = Logger.getLogger(Startup.class);

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostConstruct
    public void loadData() {
        log.info("Populando o banco de dados com dados iniciais....");

        Restaurant restaurant01 = new Restaurant("Subway", "http://res.cloudinary.com/emmet/image/upload/v1450751788/subway_xtbjsj.png");
        Restaurant restaurant02 = new Restaurant("Outback", "http://res.cloudinary.com/emmet/image/upload/v1450751788/outback_dzej9w.jpg");
        Restaurant restaurant03 = new Restaurant("McDonalds", "http://res.cloudinary.com/emmet/image/upload/v1450751787/mcdonalds_tpxbar.jpg");
        Restaurant restaurant04 = new Restaurant("Burger King", "http://res.cloudinary.com/emmet/image/upload/v1450751787/burgerking_r85emd.jpg");
        Restaurant restaurant05 = new Restaurant("Applebee's", "http://res.cloudinary.com/emmet/image/upload/v1450751788/applebees_tlmyde.jpg");


        List<Restaurant> restaurants = Arrays.asList(restaurant01, restaurant02, restaurant03, restaurant04, restaurant05);

        restaurantRepository.save(restaurants);

    }
}
