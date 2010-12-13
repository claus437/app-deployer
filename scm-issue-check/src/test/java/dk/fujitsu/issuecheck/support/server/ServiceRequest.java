/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */
package dk.fujitsu.issuecheck.support.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public class ServiceRequest {
    private byte[] request;
    private byte[] response; 

    ServiceRequest(byte[] request) {
        this.request = request;
    }

    public String getRequest() {
        return new String(request);
    }

    public String getRequestHeader(String key) {
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("^" + key + ": ([\\w\\W]*?)$", Pattern.MULTILINE);
        matcher = pattern.matcher(new String(request));

        return matcher.find() ? matcher.group(1) : null;
    }

    public String getRequestQuery() {
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("\\AGET ([\\w\\W]*?) HTTP/1.1$", Pattern.MULTILINE);
        matcher = pattern.matcher(new String(request));

        return matcher.find() ? matcher.group(1) : null;
    }

    public void setResponse(String response) {
        this.response = response.getBytes();
    }

    public void loadResponse(String resource) {
        try {
            loadResponse(getClass().getClassLoader().getResourceAsStream(resource));
        } catch (RuntimeException x) {
            throw new RuntimeException("failed loading resource " + resource, x);
        }
    }

    public void loadResponse(InputStream stream) {
        int length;
        byte[] data;
        ByteArrayOutputStream out;

        out = new ByteArrayOutputStream();
        data = new byte[4096];

        try {
            while ((length = stream.read(data)) != -1) {
                out.write(data, 0, length);
            }
        } catch (IOException x) {
            throw new RuntimeException("failed loading from stream");
        }

        response = out.toByteArray();
    }

    byte[] getResponse() {
        return response;
    }
}
