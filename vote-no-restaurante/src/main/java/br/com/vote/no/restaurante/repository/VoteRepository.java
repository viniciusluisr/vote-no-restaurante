package br.com.vote.no.restaurante.repository;

import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Vinicius on 22/12/15.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByUser(final User user);
}
