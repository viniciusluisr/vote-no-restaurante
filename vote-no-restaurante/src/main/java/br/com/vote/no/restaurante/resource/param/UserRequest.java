package br.com.vote.no.restaurante.resource.param;

import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Vinicius on 25/12/15.
 */
@Data
public class UserRequest implements Serializable {

    public UserRequest() {}

    public UserRequest(User user, List<Vote> votes) {
        this.user = user;
        this.votes = votes;
    }

    private User user;
    private List<Vote> votes;
}
