/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */
package dk.fujitsu.issuecheck;

/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public class Hook {

    public static void main(String[] args) {
        CommitCheck commitCheck;

        commitCheck = new CommitCheck(args);
        commitCheck.execute();

        Config.out(commitCheck.getMessage());
        Config.exit(commitCheck.getStatus());
    }
}
