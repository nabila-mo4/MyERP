package com.dummy.myerp.testconsumer.consumer;

import org.junit.Assert;
import org.junit.Test;

public class TestInitSpring extends ConsumerTestCase {

    /**
     * Constructeur.
     */
    public TestInitSpring() {
        super();
    }

    /**
     * Teste l'initialisation du contexte Spring
     */
    @Test
    public void testInit() {
        SpringRegistry.init();
        Assert.assertNotNull(SpringRegistry.getDaoProxy());
    }
}
