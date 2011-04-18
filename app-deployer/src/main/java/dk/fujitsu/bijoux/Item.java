package dk.fujitsu.bijoux;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 05-04-2011
 * Time: 20:11:08
 * To change this template use File | Settings | File Templates.
 */
public class Item {
    private String applicationName;
    private String status = "";
    private String artifact = "";
    private String version = "";
    private String context = "";
    private String server = "";
    private String home = "";
    private String user = "";
    private String deployed = "";
    private String latestOnBranch;
    private String latestOfficial;
    private String latest;
    private boolean scheduled;
    private Schedule schedule;

    public Item(String applicationName, String status, String artifact, String version, String context, String server, String home, String user, String deployed, String latestOnBranch, String latestOfficial, String latest, boolean scheduled) {
        this.applicationName = applicationName;
        this.status = status;
        this.artifact = artifact;
        this.version = version;
        this.context = context;
        this.server = server;
        this.home = home;
        this.user = user;
        this.deployed = deployed;
        this.latestOnBranch = latestOnBranch;
        this.latestOfficial = latestOfficial;
        this.latest = latest;
        this.scheduled = scheduled;
    }

    public String getApplicationName() {
        return applicationName;
    }

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
        return user;
    }

    public String getDeployed() {
        return deployed;
    }

    public String getLatestOnBranch() {
        return latestOnBranch;
    }

    public String getLatestOfficial() {
        return latestOfficial;
    }

    public String getLatest() {
        return latest;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }
}
