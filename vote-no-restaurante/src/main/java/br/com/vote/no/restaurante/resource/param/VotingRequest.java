package br.com.vote.no.restaurante.resource.param;

import br.com.vote.no.restaurante.model.Restaurant;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Vinicius on 28/12/15.
 */
@Data
public class VotingRequest implements Serializable {

    public VotingRequest(){}

    public VotingRequest(final Restaurant first, final Restaurant second){
        this.first = first;
        this.second = second;
    }

    private Restaurant first;
    private Restaurant second;
}
