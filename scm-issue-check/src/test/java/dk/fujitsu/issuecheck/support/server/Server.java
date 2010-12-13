/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */
package dk.fujitsu.issuecheck.support.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public class Server {
    private List<Transaction> transactionList;
    private ServerThread serveThread;
    private ServerSocket serverSocket;
    private String address;
    private int[] range;
    private int port;
    private TransactionMock mock;

    public Server() {
        range = new int[2];
        port = -1;
        transactionList = new ArrayList<Transaction>();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setListenRange(int startPort, int endPort) {
        range[0] = startPort;
        range[1] = endPort;
    }

    public void setMock(TransactionMock mock) {
        this.mock = mock;
    }

    public TransactionMock getMock() {
        return mock;
    }

    public int getPort() {
        return port;
    }

    public synchronized void start() {

        if (serveThread != null) {
            throw new RuntimeException("server already running");
        }

        try {
            serverSocket = new ServerSocket();
        } catch (IOException x) {
            throw new RuntimeException("unable to create server");
        }


        port = range[0];

        while (!serverSocket.isBound() && port < range[1]) {
            port ++;

            try {
                serverSocket.bind(new InetSocketAddress(address, port));
            } catch (IOException x) {

            }
        }

        serveThread = new ServerThread(this);
        serveThread.start();
    }


    public void stop() {
        serveThread.kill();

        try {
            serverSocket.close();
        } catch (IOException x) {
            throw new RuntimeException("error closing server, " + x.getMessage(), x);
        }
    }

    Socket accept() throws IOException {
        return serverSocket.accept();
    }


}
