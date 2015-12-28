package br.com.vote.no.restaurante.template.loader;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.vote.no.restaurante.model.Ranking;
import br.com.vote.no.restaurante.model.Restaurant;
import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.model.Vote;

/**
 * Created by Vinicius on 24/12/15.
 */
public class RankingTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Ranking.class).addTemplate("valid", new Rule() {
            {
                add("id", random(Long.class, range(1L, 200L)));
                add("restaurant", one(Restaurant.class, "valid"));
                add("user", one(User.class, "valid"));
                add("points", random(Integer.class, range(1, 200)));
            }
        });
    }
}
