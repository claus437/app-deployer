package dk.fujitsu.bijoux;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 06-04-11
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
public class Job {
    private String minute;
    private String hour;
    private String dom;
    private String month;
    private String dow;
    private String execute;

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDow() {
        return dow;
    }

    public void setDow(String dow) {
        this.dow = dow;
    }

    public String getExecute() {
        return execute;
    }

    public void setExecute(String execute) {
        this.execute = execute;
    }

    public Job clone() {
        Job clone;

        clone = new Job();

        clone.minute = minute;
        clone.hour = hour;
        clone.dom = dom;
        clone.month = month;
        clone.dow = dow;
        clone.execute = execute;

        return clone;
    }
}
