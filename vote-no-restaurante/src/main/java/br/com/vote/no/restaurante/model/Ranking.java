package br.com.vote.no.restaurante.model;

import br.com.vote.no.restaurante.model.base.BaseEntity;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Vinicius on 24/12/15.
 */
@ApiModel("Objeto que representa um Restaurante")
@Data
@Entity
public class Ranking extends BaseEntity {

    public Ranking(){}

    public Ranking(final Restaurant restaurant, final Integer points) {
        this.restaurant = restaurant;
        this.points = points;
    }

    @ApiModelProperty(value = "O ID do Ranking")
    @Id
    @GeneratedValue
    private final Long id = null;

    @ApiModelProperty(value = "O Restaurante correspondente a este ranking")
    @JoinColumn(nullable = false)
    @ManyToOne
    private Restaurant restaurant;

    @ApiModelProperty(value = "a pontuação do Restaurante correspondente a este ranking")
    @Column()
    private Integer points;
}
