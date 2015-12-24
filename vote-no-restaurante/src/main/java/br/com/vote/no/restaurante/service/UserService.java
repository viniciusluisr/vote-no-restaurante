package br.com.vote.no.restaurante.service;

import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;

import java.util.List;
import java.util.Optional;

/**
 * Created by Vinicius on 22/12/15.
 */
public interface UserService {

    Optional<User> createUser(final User user, final List<Vote> votes);
}
