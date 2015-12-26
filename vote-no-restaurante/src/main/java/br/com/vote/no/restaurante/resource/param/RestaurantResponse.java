package br.com.vote.no.restaurante.resource.param;

import br.com.vote.no.restaurante.model.Restaurant;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Vinicius on 24/12/15.
 */
@Data
public class RestaurantResponse implements Serializable {

    public RestaurantResponse() {}

    public RestaurantResponse(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    private List<Restaurant> restaurants;
}
