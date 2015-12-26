package br.com.vote.no.restaurante.resource;

import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Voting;
import br.com.vote.no.restaurante.resource.param.RankingResponse;
import br.com.vote.no.restaurante.resource.param.RestaurantResponse;
import br.com.vote.no.restaurante.service.RankingService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Vinicius on 25/12/15.
 */
@RestController
@RequestMapping(value = "/v1/rankings")
@Validated
@Api(value = "API de Rankings", description = "Essa API tem como objetivo expor as operações para consultar o ranking por usuário e o ranking geral de restaurantes",
        basePath = "/v1/rankings", produces = "application/json")
public class RankingResource {

    @Autowired
    private RankingService rankingService;

    @ApiOperation(value = "Consulta o ranking por usuário", notes = "Retorna a lista de rankings do usuário", response = RankingResponse.class)
         @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
         public HttpEntity<RankingResponse> getRankingByUser(@PathVariable final Long userId) {
        return new ResponseEntity<RankingResponse>(new RankingResponse(rankingService.getRankingByUser(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna o ranking geral de restaurantes", notes = "Retorna o ranking geral de restaurantes", response = RankingResponse.class)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<RankingResponse> getGeneralRanking() {
        return new ResponseEntity<RankingResponse>(new RankingResponse(rankingService.getGeneralRanking()), HttpStatus.OK);
    }
}
