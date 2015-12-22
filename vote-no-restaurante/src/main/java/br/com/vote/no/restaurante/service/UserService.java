package br.com.vote.no.restaurante.service;

import br.com.vote.no.restaurante.model.User;

import java.util.Optional;

/**
 * Created by Vinicius on 22/12/15.
 */
public interface UserService {

    Optional<User> createUser(final User user);
}
