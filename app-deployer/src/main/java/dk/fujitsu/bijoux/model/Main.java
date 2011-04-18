package dk.fujitsu.bijoux.model;

import dk.fujitsu.bijoux.DataBase;
import dk.fujitsu.bijoux.Item;
import dk.fujitsu.bijoux.Schedule;
import org.ajax4jsf.ajax.html.HtmlAjaxCommandLink;

import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 05-04-2011
 * Time: 17:58:13
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static final DataBase data = DataBase.getInstance();
    private Item selectedItem;
    private String version;
    private Schedule schedule;

    public Main() {
        selectedItem = data.getConfigItems().get(0);
        schedule = data.getScheduleFor(selectedItem.getApplicationName());
    }
    
    public Item getConfig() {
        return selectedItem;
    }

    public List<Item> getConfigItems() {
        return data.getConfigItems();
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[] getVersions() {
        return data.getVersions(selectedItem.getArtifact());
    }

    public void onUpdateSchedule(ActionEvent e) {
        Schedule schedule;
        String appName;

        appName = selectedItem.getApplicationName();

        schedule = data.getScheduleFor(appName);
        schedule.setJobs(this.schedule.getJobs());

        data.persist(schedule, appName);
    }
    
    public void onDeploy(ActionEvent e) {
        System.out.println("deploying version " + version);
    }

    public void onPick(ActionEvent e) {
        this.version = ((HtmlAjaxCommandLink) e.getSource()).getValue().toString();
        System.out.println("version picked " + version);
    }

    public void onApplicationSelect(ActionEvent e) {
        String appName;

        appName = ((HtmlAjaxCommandLink) e.getSource()).getValue().toString();
        this.selectedItem = data.getConfigItemByAppName(appName);
        this.schedule = data.getScheduleFor(selectedItem.getApplicationName()).clone();
        this.version = "";
    }
}
