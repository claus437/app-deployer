package dk.fujitsu.bijoux;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 31-03-2011
 * Time: 19:48:54
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationView {
    private String version = "2.31.3";

    public String getVersion() {
        System.out.println("get version");
        return version + " " + System.currentTimeMillis();
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
