package br.com.vote.no.restaurante.resource;

import br.com.vote.no.restaurante.model.Restaurant;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Vinicius on 24/12/15.
 */
@Data
@XmlRootElement
public class RestaurantResponse {

    public RestaurantResponse() {}

    public RestaurantResponse(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    private List<Restaurant> restaurants;
}
