package br.com.vote.no.restaurante.template.loader;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.User;

/**
 * Created by Vinicius on 22/12/15.
 */
public class UserTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(User.class).addTemplate("valid", new Rule() {
            {
                add("id", random(Long.class, range(1L, 200L)));
                add("name", random("Vinícius Rodrigues", "Giovani Mendes", "Amanda Cristina", "Pablo Monteiro", "Douglas Schmitz"));
                add("email", random("viniciusluisr@gmail.com","giovani.mendes@outlook.com","amanda.cris@icloud.com", "plano.monteiro@gmail.com", "douglas.schmitz@bol.com.br"));
            }
        });

        Fixture.of(User.class).addTemplate("validWithoutId", new Rule() {
            {
                add("name", random("Vinícius Rodrigues", "Giovani Mendes", "Amanda Cristina", "Pablo Monteiro", "Douglas Schmitz"));
                add("email", random("viniciusluisr@gmail.com","giovani.mendes@outlook.com","amanda.cris@icloud.com", "plano.monteiro@gmail.com", "douglas.schmitz@bol.com.br"));
            }
        });
    }
}
