package br.com.vote.no.restaurante.model;

import br.com.vote.no.restaurante.model.base.BaseEntity;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Vinicius on 22/12/15.
 */
@ApiModel("Objeto que representa um Voto")
@Data
@Entity
public class Vote extends BaseEntity {

    public Vote() {}

    public Vote(Restaurant restaurant, User user) {
        this.restaurant = restaurant;
        this.user = user;
    }

    @ApiModelProperty(value = "O ID do Voto")
    @Id
    @GeneratedValue
    private final Long id = null;

    @ApiModelProperty(value = "O restaurante votado")
    @JoinColumn(nullable = false)
    @ManyToOne
    private Restaurant restaurant;

    @ApiModelProperty(value = "O usuário votante")
    @JoinColumn(nullable = false)
    @ManyToOne
    private User user;

}
