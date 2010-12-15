/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */

package dk.fujitsu.issuecheck.scm;

import dk.fujitsu.issuecheck.Config;
import dk.fujitsu.issuecheck.Shell;

import java.util.Arrays;
import java.util.List;

/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public class GitService implements ScmService {

    @Override
    public List<String> getMessages(String... arguments) {
        Shell shell;
        String[] entries;

        if (arguments.length != 3) {
            throw new RuntimeException("getMessages takes 3 arguments: object reference, old revision and new revision");
        }
        
        shell = new Shell();

        try {
            shell.execute(new String[]{
                    Config.get("scm.git.bin"),
                    // "--git-dir=" + Config.get("scm.git.dir") + "\\.git",
                    // "--work-tree=" + Config.get("scm.git.dir"),
                    "log",
                    "--pretty=format:%s",
                    arguments[1] + ".." + arguments[2]});
        } catch (Throwable x) {
            throw new RuntimeException("unable to execute git, " + x.getMessage(), x);
        }

        if (shell.getExitValue() != 0) {
            throw new RuntimeException("unable to retrieve git log for package " + Arrays.asList(arguments) + " - stdout: " + shell.getStandard() + " errout: " + shell.getError());
        }

        entries = shell.getStandard().toString().split(System.getProperty("line.separator"));

        return Arrays.asList(entries);
    }
}
