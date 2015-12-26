package br.com.vote.no.restaurante.resource.param;

import br.com.vote.no.restaurante.model.Ranking;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Vinicius on 25/12/15.
 */
@Data
public class RankingResponse implements Serializable {

    public RankingResponse() {}

    public RankingResponse(final List<Ranking> rankings) {
        this.rankings = rankings;
    }

    private List<Ranking> rankings;
}
