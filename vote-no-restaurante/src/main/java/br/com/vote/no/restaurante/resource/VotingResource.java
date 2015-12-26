package br.com.vote.no.restaurante.resource;

import br.com.vote.no.restaurante.model.Voting;
import br.com.vote.no.restaurante.service.VotingService;
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

import java.util.Optional;

/**
 * Created by Vinicius on 24/12/15.
 */
@RestController
@RequestMapping(value = "/v1/votings")
@Validated
@Api(value = "API de Votacao", description = "Essa API tem como objetivo expor as operações necessárias para o mecanismo de votação", basePath = "/v1/votings", produces = "application/json")
public class VotingResource {

    @Autowired
    private VotingService votingService;

    @ApiOperation(value = "Inicia a votação com dois restaurantes iniciais", notes = "Retorna um objeto Voting", response = Voting.class)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Voting> beginVoting() {
        Optional<Voting> voting = votingService.beginVoting();
        return new ResponseEntity<>(voting.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Realiza um voto escolhendo o restaurante a partir do id informado", notes = "Retorna um objeto Voting", response = Voting.class)
    @RequestMapping(value = "/{restaurantId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Voting> voting(@PathVariable final Long restaurantId) {
        return new ResponseEntity<Voting>(votingService.voting(restaurantId).get(), HttpStatus.OK);
    }

}
