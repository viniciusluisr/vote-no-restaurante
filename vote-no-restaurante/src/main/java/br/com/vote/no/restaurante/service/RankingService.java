package br.com.vote.no.restaurante.service;

import br.com.vote.no.restaurante.model.Ranking;
import br.com.vote.no.restaurante.model.User;

import java.util.List;

/**
 * Created by Vinicius on 24/12/15.
 */
public interface RankingService {

    List<Ranking> getGeneralRanking();
    List<Ranking> getRankingByUser(final Long userId);

}
