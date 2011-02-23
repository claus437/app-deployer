/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */
package dk.fujitsu.issuecheck;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Arrays;

/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public class Shell {
    private static final Logger LOGGER = Logger.getLogger(Shell.class);
    private StringBuffer standard;
    private StringBuffer error;
    private Integer exitValue;


    public void execute(String[] args) throws IOException, InterruptedException {
        StreamGobbler std;
        StreamGobbler err;
        Runtime runtime;
        Process process;

        LOGGER.debug("executing " + Arrays.asList(args));
        runtime = Runtime.getRuntime();
        process = runtime.exec(args);

        exitValue = null;
        standard = new StringBuffer();
        error = new StringBuffer();


        std = new StreamGobbler(process.getInputStream(), standard);
        err = new StreamGobbler(process.getErrorStream(), error);

        std.start();
        err.start();

        process.waitFor();

        LOGGER.debug("process executed");

        while (std.isAlive() || err.isAlive()) {
            Thread.sleep(10);
        }

        LOGGER.debug("stream gobblers done - std:" + standard.toString() + " err: " + err.toString());

        exitValue = process.exitValue();
    }

    public StringBuffer getStandard() {
        return standard;
    }

    public StringBuffer getError() {
        return error;
    }

    public Integer getExitValue() {
        return exitValue;
    }

    private class StreamGobbler extends Thread {
        private InputStream stream;
        private StringBuffer buffer;

        StreamGobbler(InputStream stream, StringBuffer buffer) {
            this.stream = stream;
            this.buffer = buffer;
        }

        public void run() {
            Reader reader;
            char[] data;
            int length;

            data = new char[4096];

            reader = new BufferedReader(new InputStreamReader(stream));
            try {
                while((length = reader.read(data)) != -1) {
                    buffer.append(data, 0, length);
                }
            } catch (IOException x) {
                System.out.println("failed reading line " + x.getMessage());
            }

            LOGGER.debug("read " + buffer.toString());
        }
    }
}
