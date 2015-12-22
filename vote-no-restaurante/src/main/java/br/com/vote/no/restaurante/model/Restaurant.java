package br.com.vote.no.restaurante.model;

import br.com.vote.no.restaurante.model.base.BaseEntity;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Vinicius on 22/12/15.
 */
@ApiModel("Objeto que representa um Restaurante")
@Data
@Entity
public class Restaurant extends BaseEntity {


    public Restaurant() {}

    public Restaurant(String name, String logo) {
        this.name = name;
        this.logo = logo;

    }

    @ApiModelProperty(value = "O ID do Usuário")
    @Id
    @GeneratedValue
    private final Long id = null;

    @ApiModelProperty(value = "O nome do Restaurante")
    @Size(min = 1, max = 200, message = "O Nome deve conter no mínimo {min} caracteres e no máximo {max} caracteres.")
    @NotNull(message = "Por favor, preencha o Nome!!")
    @Column(nullable = false, length = 200)
    private String name;

    @ApiModelProperty(value = "O Logo do Restaurante")
    @Size(min = 1, max = 200, message = "A URL do Logo deve conter no mínimo {min} caracteres e no máximo {max} caracteres.")
    @NotNull(message = "Por favor, preencha a URL do Logo!!")
    @Column(nullable = false, length = 380)
    private String logo;
}
