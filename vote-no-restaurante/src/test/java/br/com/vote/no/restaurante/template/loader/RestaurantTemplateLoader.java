package br.com.vote.no.restaurante.template.loader;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.vote.no.restaurante.model.Restaurant;

/**
 * Created by Vinicius on 22/12/15.
 */
public class RestaurantTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Restaurant.class).addTemplate("valid", new Rule() {
            {
                add("id", random(Long.class, range(1L, 200L)));
                add("name", random("Habib's", "Black Dog", "Mr. Mills", "Bassano", "White Castle"));
                add("logo", random("http://res.cloudinary.com/emmet/image/upload/v1450802797/Rizzo11_sctvic.png",
                        "http://res.cloudinary.com/emmet/image/upload/v1450802738/The_Muppets-0014-20110912-28_psy9km.jpg",
                        "http://res.cloudinary.com/emmet/image/upload/v1450802737/piggy_qseiqx.png",
                        "http://res.cloudinary.com/emmet/image/upload/v1450802736/638full-poster_hnz8op.jpg",
                        "http://res.cloudinary.com/emmet/image/upload/v1450802706/fozzie_big_uwvzsg.png"));
            }
        });
    }
}
