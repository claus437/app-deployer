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
        int status;

        commitCheck = new CommitCheck(args);
        commitCheck.execute();

        status = commitCheck.getStatus();
        if (status > 0) {
            Config.err(commitCheck.getMessage());
        } else {
            Config.out(commitCheck.getMessage());
        }

        Config.exit(status);
    }
}
