/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */
package dk.fujitsu.issuecheck;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;


/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public class ShellTest {

    @Test
    public void testExecute() throws Exception {
        Shell shell;

        shell = new Shell();
        shell.execute(new String[]{"/bin/bash", "-c", "echo \"Hello World\""});

        System.out.println("STD: " + shell.getStandard());
        System.out.println("STD: " + shell.getError());
        System.out.println("CODE:" + shell.getExitValue());
    }

    @Test
    public void testStandardStream() throws Exception {
        Shell shell;
        String result;

        shell = new Shell();

        shell.execute(getArguments("echo", "Hello World"));
        result = shell.getStandard().toString();

        if ('\"' == result.charAt(0)) {
            result = result.substring(1, result.length() - 1);
        }

        Assert.assertEquals("Hello World" + System.getProperty("line.separator"), result);
        Assert.assertEquals("", shell.getError().toString());
        Assert.assertEquals(new Integer(0), shell.getExitValue());
    }

    @Test
    public void testErrorStream() throws Exception {
        Shell shell;
        String result;
        shell = new Shell();

        shell.execute(getArguments("echo", "Hello World", "1>&2"));
        result = shell.getError().toString();

        if ('\"' == result.charAt(0)) {
            result = result.substring(1, result.length() - 1);
        }

        Assert.assertEquals("", shell.getStandard().toString());
        Assert.assertTrue("Hello World".equals(result));
        Assert.assertEquals(new Integer(0), shell.getExitValue());
    }

    @Test
    @Ignore
    public void testError() throws Exception {
        Shell shell;

        shell = new Shell();

        shell.execute(getArguments("no_such_command"));
        Assert.assertEquals("", shell.getStandard().toString());
        Assert.assertFalse(shell.getError().toString().isEmpty());
        Assert.assertTrue(shell.getExitValue() != 0);
    }

    private String[] getArguments(String... arguments) throws Exception {
        String[] args;
        StringBuffer buffer;

        if (System.getenv().containsKey("windir")) {
            args = new String[2 + arguments.length];
            args[0] = System.getenv("windir") + "\\system32\\cmd.exe";
            args[1] = "/C";

            System.arraycopy(arguments, 0, args, 2, arguments.length);

            return args;
        }

        buffer = new StringBuffer();
        for (String arg : arguments) {
            buffer.append(arg);
            buffer.append(" ");
        }
        
        args = new String[3];
        args[0] = "/bin/bash";
        args[1] = "-c";
        args[2] = buffer.toString().substring(0, buffer.length() - 1);

        return args;
    }
}

