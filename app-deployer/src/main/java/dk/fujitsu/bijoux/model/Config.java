package dk.fujitsu.bijoux.model;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 05-04-2011
 * Time: 17:58:38
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    private String status = "";
    private String artifact = "";
    private String version = "";
    private String context = "";
    private String server = "";
    private String home = "";
    private String user = "";

    public String getStatus() {
        return status;
    }

    public String getArtifact() {
        return artifact;
    }

    public String getVersion() {
        return version;
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
        return Long.toString(System.currentTimeMillis());
    }
}
