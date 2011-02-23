/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */
package dk.fujitsu.issuecheck;

import org.apache.log4j.Logger;

import java.util.Arrays;


/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public class Hook {
    private static Logger LOGGER = Logger.getLogger(Hook.class);

    public static void main(String[] args) {
        CommitCheck commitCheck;
        int status;

        LOGGER.debug("executed with arguments " + Arrays.asList(args));

        commitCheck = new CommitCheck(args);
        commitCheck.execute();

        status = commitCheck.getStatus();
        if (status > 0) {
            Config.err(commitCheck.getMessage());
        } else {
            Config.out(commitCheck.getMessage());
        }

        LOGGER.debug("completed request with exit code " + status);
        Config.exit(status);
    }
}
