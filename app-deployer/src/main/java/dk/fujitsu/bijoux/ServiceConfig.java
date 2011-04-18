package dk.fujitsu.bijoux;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 31-03-2011
 * Time: 20:31:08
 * To change this template use File | Settings | File Templates.
 */
public class ServiceConfig {
    private static final Map<String, ServiceConfig> SERVICE_CONFIG_MAP = new HashMap<String, ServiceConfig>();

    static {
        ServiceConfig config;

        config = new ServiceConfig();
        config.status = "running";
        config.artifact = "dk.telia.cas.cas";
        config.version = "2.31.1";
        config.released = "20.01.11 17:45";
        config.context = "castesttr";
        config.server = "castesttr.test.telia.dk";
        config.home = "/opt/castesttr";
        config.user = "castesttr";

        SERVICE_CONFIG_MAP.put(config.server + "/" + config.context, config);
        new TestThread(config).start();
    }

    private String status;
    private String artifact;
    private String version;
    private String released;
    private String context;
    private String server;
    private String home;
    private String user;

    public String getStatus() {
        return status;
    }

    public String getArtifact() {
        return artifact;
    }

    public String getVersion() {
        return version;
    }

    public String getReleased() {
        return released;
    }

    public String getContext() {
        return context;
    }

    public String getServer() {
        return server;
    }

    public String getHome() {
        return home;
    }

    public String getUser() {
        return user;
    }

    public void refresh() {

    }

    public static ServiceConfig getConfig(String service) {
        ServiceConfig config;

        config = SERVICE_CONFIG_MAP.get(service);
        if (config == null) {
            throw new BijouxException("unknown service " + service);
        }

        return SERVICE_CONFIG_MAP.get(service);
    }


    static final class TestThread extends Thread {
        ServiceConfig config;

        public TestThread(ServiceConfig config) {
            this.config = config;
            start();
        }

        public void run() {
            System.out.println("STARTED");

            while (true) {
                config.released = new SimpleDateFormat("dd.MM.yy HH:mm:ss").format(new Date());
            }
        }
    }
}
