/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */
package dk.fujitsu.issuecheck.ims;

import dk.fujitsu.issuecheck.Config;
import dk.fujitsu.issuecheck.support.server.Server;
import dk.fujitsu.issuecheck.support.server.ServiceRequest;
import dk.fujitsu.issuecheck.support.server.TransactionMock;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodRunner;
import org.wooddog.woodstub.junit.annotation.Excludes;
import org.wooddog.woodstub.junit.annotation.Stubs;

/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
@RunWith(WoodRunner.class)
@Stubs({Config.class})
@Excludes({"org\\.junit.*", "sun\\.com.*", "net\\.sourceforge\\.cobertura\\..*"})
public class RallyServiceTest implements StubListener  {
    private String issueTypes = "defect, task";
    private static String host = "http://localhost";
    private static Server server;

    @BeforeClass
    public static void init() {
        server = new Server();
        server.setAddress("localhost");
        server.setListenRange(20000, 21000);
        server.start();
        host += ":" + server.getPort();

        System.out.println("server started listening on " + host);
    }

    @AfterClass
    public static void destroy() {
        server.stop();

        System.out.println("server stopped");
    }

    @Test
    public void testIsIssueFound() throws Exception {
        RallyService service;

        server.setMock(new TransactionMock() {
            @Override
            public void onService(ServiceRequest request) {
                Assert.assertEquals("/defect?query=(FormattedID%20=%20DE1202)&fetch=true", request.getRequestQuery());
                Assert.assertEquals("Basic cmFsbHlfdXNlcjpyYWxseV9wYXNzd29yZA==", request.getRequestHeader("Authorization"));

                request.loadResponse("dk/fujitsu/issuecheck/ims/http-rs-rally-defect.txt");
            }
        });

        WoodRunner.addListener(this);

        service = new RallyService();
        Assert.assertTrue("found no issue ", service.isIssue("DE1202"));
    }

    @Test
    public void testIsIssueNotFound() throws Exception {
        RallyService service;
        NoIssueMock mock;

        mock = new NoIssueMock();
        server.setMock(mock);

        WoodRunner.addListener(this);

        service = new RallyService();

        Assert.assertFalse("found issue ", service.isIssue("DE120"));
        mock.verify();
    }


    @Override
    public void invoked(StubEvent stubEvent) {
        if ("get".equals(stubEvent.getMethodName())) {
            if (("ims.rally.issueTypes".equals(stubEvent.getObject(1)))) {
                stubEvent.setResult(issueTypes);
            }

            if (("ims.rally.service").equals(stubEvent.getObject(1))) {
                stubEvent.setResult(host);
            }

            if (("ims.rally.user").equals(stubEvent.getObject(1))) {
                stubEvent.setResult("rally_user");
            }

            if (("ims.rally.password").equals(stubEvent.getObject(1))) {
                stubEvent.setResult("rally_password");
            }
        }
    }

    class NoIssueMock implements TransactionMock {
        boolean[] issueTypes = new boolean[3];

        public void onService(ServiceRequest request) {
            System.out.println(request.getRequestQuery());
            
            if (request.getRequestQuery().startsWith("/defect")) {
                Assert.assertEquals("/defect?query=(FormattedID%20=%20DE120)&fetch=true", request.getRequestQuery());
                issueTypes[0] = true;
            }

            if (request.getRequestQuery().startsWith("/task")) {
                Assert.assertEquals("/task?query=(FormattedID%20=%20DE120)&fetch=true", request.getRequestQuery());
                issueTypes[1] = true;
            }

            if (request.getRequestQuery().startsWith("/hieracicrequirement")) {
                Assert.assertEquals("/hieracicrequirement?query=(FormattedID%20=%20DE120)&fetch=true", request.getRequestQuery());
                issueTypes[2] = true;
            }

            Assert.assertEquals("Basic cmFsbHlfdXNlcjpyYWxseV9wYXNzd29yZA==", request.getRequestHeader("Authorization"));
            request.loadResponse("dk/fujitsu/issuecheck/ims/http-rs-rally-empty-query.txt");

            
        }

        public void verify() {
            Assert.assertTrue("defect", issueTypes[0]);
            Assert.assertTrue("task", issueTypes[1]);
            //Assert.assertTrue("hieracicrequirement", issueTypes[2]);
        }
    }
}
