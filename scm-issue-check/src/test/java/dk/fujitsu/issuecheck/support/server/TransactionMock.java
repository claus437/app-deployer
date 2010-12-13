/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */
 package dk.fujitsu.issuecheck.support.server;


/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public interface TransactionMock {
    public void onService(ServiceRequest request);
}
