package br.com.vote.no.restaurante.resource;

import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;
import br.com.vote.no.restaurante.resource.param.UserRequest;
import br.com.vote.no.restaurante.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Vinicius on 26/12/15.
 */
@RestController
@RequestMapping(value = "/v1/users")
@Validated
@Api(value = "API de Usuarios", description = "Essa API tem como objetivo expor as operações relacionadas a usuários", basePath = "/v1/users", produces = "application/json")
public class UserResource {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Cria um usuário, computa seus votos e atualiza o ranking de restaurantes", notes = "Deve receber um objeto usuário e a lista de votos", response = User.class)
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<User> createUser(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<User>(userService.createUser(userRequest.getUser(), userRequest.getVotes()).get(), HttpStatus.CREATED);
    }
}