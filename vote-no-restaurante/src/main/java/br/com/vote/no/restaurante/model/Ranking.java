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
//@Data
@Entity
public class Ranking extends BaseEntity {

    public Ranking(){}

    public Ranking(final Restaurant restaurant, final User user, final Integer points) {
        this.restaurant = restaurant;
        this.points = points;
        this.user = user;
    }

    @ApiModelProperty(value = "O ID do Ranking")
    @Id
    @GeneratedValue
    private final Long id = null;

    @ApiModelProperty(value = "O Restaurante correspondente a este ranking")
    @JoinColumn(nullable = false)
    @ManyToOne
    private Restaurant restaurant;

    @ApiModelProperty(value = "O usuário correspondente a este ranking")
    @JoinColumn(nullable = true)
    @ManyToOne
    private User user;

    @ApiModelProperty(value = "a pontuação do Restaurante correspondente a este ranking")
    @Column()
    private Integer points;

    public Long getId() {
        return id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
