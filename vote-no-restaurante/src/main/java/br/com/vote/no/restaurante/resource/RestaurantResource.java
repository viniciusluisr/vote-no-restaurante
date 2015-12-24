package br.com.vote.no.restaurante.resource;

import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.Voting;
import br.com.vote.no.restaurante.service.RestaurantService;
import br.com.vote.no.restaurante.service.VotingService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by Vinicius on 24/12/15.
 */
@RestController
@RequestMapping(value = "/v1/restaurants")
@Validated
@Api(value = "API de Restaurantes", description = "Essa API tem como objetivo expor as operações relacionadas a os restaurantes", basePath = "/v1/restaurants", produces = "application/json")
public class RestaurantResource {

    @Autowired
    private RestaurantService restaurantService;

    @ApiOperation(value = "Busca todos os restaurantes do sistema", notes = "Retorna todos os restaurantes", response = Restaurant.class)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<RestaurantResponse> findAll() {
        List<Restaurant> restaurants = restaurantService.findAll();
        return new ResponseEntity<>(new RestaurantResponse(restaurants), HttpStatus.OK);
    }
}
