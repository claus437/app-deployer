/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */
package dk.fujitsu.issuecheck.support.server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public class ServerThread extends Thread {
    private List<Transaction> transactionList;
    private Server server;
    private boolean signal;

    ServerThread(Server server) {
        this.server = server;
        this.transactionList = new ArrayList<Transaction>();
    }

    public void run() {
        Transaction transaction;
        Socket socket;

        while (!signal) {
            try {
                socket = server.accept();
                transaction = new Transaction(this);
                transaction.setMock(server.getMock());
                transaction.setSocket(socket);
                transactionList.add(transaction);
                transaction.start();
            }  catch (SocketException x) {
                if (signal) {
                    return;
                }
                throw new RuntimeException(x);
            } catch (IOException x) {
                throw new RuntimeException(x);
            }
        }
    }

    void onComplete(Transaction transaction) {
        transactionList.remove(transaction);
    }

    public void kill() {
        signal = true;

        while (!transactionList.isEmpty()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException x) {
                throw new RuntimeException(x);
            }
        }
    }
}