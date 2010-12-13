/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */
package dk.fujitsu.issuecheck;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wooddog.woodstub.junit.AssertionFlow;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodRunner;
import org.wooddog.woodstub.junit.annotation.Excludes;
import org.wooddog.woodstub.junit.annotation.Stubs;

/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
@RunWith (WoodRunner.class)
@Stubs ({CommitCheck.class, Config.class})
@Excludes ({"org\\.junit.*", "sun\\.com.*", "net\\.sourceforge\\.cobertura\\..*"})
public class HookTest {

    @Test
    public void testHookOk() {
        AssertionFlow flow;

        flow = new AssertionFlow();
        flow.add(new StubListener() {
            @Override
            public void invoked(StubEvent stubEvent) {
                Assert.assertEquals("dk.fujitsu.issuecheck.CommitCheck", stubEvent.getMethodName());
                Assert.assertEquals("refs/names/master", ((String[]) stubEvent.getObject(1))[0]);
                Assert.assertEquals("oldrev", ((String[]) stubEvent.getObject(1))[1]);
                Assert.assertEquals("newrev", ((String[]) stubEvent.getObject(1))[2]);
            }
        });

        flow.add(new StubListener() {
            @Override
            public void invoked(StubEvent stubEvent) {
                Assert.assertEquals("execute", stubEvent.getMethodName());
            }
        });

        flow.add(new StubListener() {
            @Override
            public void invoked(StubEvent stubEvent) {
                Assert.assertEquals("getMessage", stubEvent.getMethodName());
                stubEvent.setResult("Hello World");
            }
        });

        flow.add(new StubListener() {
            @Override
            public void invoked(StubEvent stubEvent) {
                Assert.assertEquals("out", stubEvent.getMethodName());
                Assert.assertEquals("Hello World", stubEvent.getObject(1).toString());
            }
        });

        flow.add(new StubListener() {
            @Override
            public void invoked(StubEvent stubEvent) {
                Assert.assertEquals("getStatus", stubEvent.getMethodName());
                stubEvent.setResult(1);
            }
        });
        
        flow.add(new StubListener() {
            @Override
            public void invoked(StubEvent stubEvent) {
                Assert.assertEquals("exit", stubEvent.getMethodName());
                Assert.assertEquals(1, stubEvent.getInt(1));
            }
        });

        WoodRunner.addListener(flow);
        
        flow.start();

        new Hook();
        Hook.main(new String[]{"refs/names/master", "oldrev", "newrev"});

        Assert.assertTrue("not all expected methods were called ", flow.isComplete());

    }
}
