package br.com.vote.no.restaurante.model;

import br.com.vote.no.restaurante.model.base.BaseEntity;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Vinicius on 22/12/15.
 */
@ApiModel("Objeto que representa um Voto")
@Data
public class Voting extends BaseEntity {

    public Voting() {

    }

    public Voting(final Restaurant first, final Restaurant second) {
        this.first = first;
        this.second = second;
    }

    @ApiModelProperty(value = "O primeiro restaurante da Votação")
    private Restaurant first;

    @ApiModelProperty(value = "O segundo restaurante da Votação")
    private Restaurant second;
}
