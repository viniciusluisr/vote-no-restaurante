package br.com.vote.no.restaurante.model;

import br.com.vote.no.restaurante.model.base.BaseEntity;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Vinicius on 22/12/15.
 */
@ApiModel("Objeto que representa um Usuário")
@Data
@Entity
public class User extends BaseEntity {

    public User() {}

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    @ApiModelProperty(value = "O ID do Usuário")
    @Id
    @GeneratedValue
    private final Long id = null;

    @ApiModelProperty(value = "O email do Usuário")
    @Email(message="Email inválido! Por favor, preencha um email válido.")
    @Size(min = 1, max = 100, message = "O Emdereço deve conter no mínimo {min} caracteres e no máximo {max} caracteres.")
    @NotNull(message = "Por favor, preencha o Email!")
    @Column(nullable = false, length = 100)
    private String email;

    @ApiModelProperty(value = "O nome do Usuário")
    @Size(min = 1, max = 200, message = "O Nome deve conter no mínimo {min} caracteres e no máximo {max} caracteres.")
    @NotNull(message = "Por favor, preencha o Nome!!")
    @Column(nullable = false, length = 200)
    private String name;
}
