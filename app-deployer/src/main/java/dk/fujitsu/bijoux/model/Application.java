package dk.fujitsu.bijoux.model;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 05-04-2011
 * Time: 19:48:51
 * To change this template use File | Settings | File Templates.
 */
public class Application {
    private String name;
    private String version;
    private String deployed;
    private String status;

    public Application(String name, String version, String deployed, String status) {
        this.name = name;
        this.version = version;
        this.deployed = deployed;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getDeployed() {
        return deployed;
    }

    public String getStatus() {
        return status;
    }
}
