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
import java.net.Socket;


/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public class Transaction extends Thread {
    private ServerThread server;
    private Socket socket;
    private TransactionMock mock;

    public Transaction(ServerThread server) {
        this.server = server;
    }

    public void setMock(TransactionMock mock) {
        this.mock = mock;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void start() {
        super.start();
    }
    
    public void run() {
        ServiceRequest serviceRequest;
        byte[] request;

        try {
            request = readRequest(socket.getInputStream());
            serviceRequest = new ServiceRequest(request);

            mock.onService(serviceRequest);
            socket.getOutputStream().write(serviceRequest.getResponse());
            socket.close();
            server.onComplete(this);
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    private byte[] readRequest(InputStream stream) {
        byte[] eof;
        ByteArrayOutputStream out;
        int pos;
        int b;

        eof = new byte[]{13, 10, 13, 10};
        out = new ByteArrayOutputStream();
        pos = 0;

        try {
            while(pos != eof.length) {
                b = stream.read();
                out.write(b);
                pos = b == eof[pos] ?  pos + 1 : 0;
            }
        } catch (IOException x) {
            throw new RuntimeException("failed reading request, " + x.getMessage(), x);
        }

        return out.toByteArray();
    }
}
