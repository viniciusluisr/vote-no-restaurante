package br.com.vote.no.restaurante.service;

import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.repository.UserRepository;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Vinicius on 22/12/15.
 */
@Service
public class UserServiceProvider implements UserService {

    private Logger log = Logger.getLogger(UserServiceProvider.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> createUser(final User user) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(user.getEmail());
        Preconditions.checkNotNull(user.getName());

        log.info("Cadastrando um novo usuário: " + user.toString());

        User found = userRepository.findByEmail(user.getEmail());
        if(found != null) return Optional.of(found);

        return Optional.of(userRepository.save(user));
    }

}
