package br.com.vote.no.restaurante.support;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.BeforeClass;

/**
 * Classe de suporte para testes com FixtureFactory
 * Created by Vinicius on 22/12/15.
 */
public abstract class TestFixtureSupport extends TestSupport {

    /**
     * Before test class.
     */
    @BeforeClass
    public static void beforeTestClass() {
        FixtureFactoryLoader.loadTemplates("br.com.vote.no.restaurante.template.loader");
    }

}