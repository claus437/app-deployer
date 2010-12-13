/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */
package dk.fujitsu.issuecheck;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public class ConfigTest {

    @Test
    public void testConfigFactory() {
        ConfigTest test1;
        ConfigTest test2;

        Config.set("test.implementation", getClass().getCanonicalName());

        test1 = Config.getInstance("test");
        test2 = Config.getInstance("test");

        Assert.assertEquals(test1, test2);
    }

    @Test
    public void testConfigKeys() {

        Config.set("test", "key");
        Assert.assertEquals("key", Config.get("test"));
    }
}
