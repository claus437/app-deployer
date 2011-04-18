package dk.fujitsu.bijoux;

import dk.fujitsu.bijoux.model.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 05-04-2011
 * Time: 20:08:32
 * To change this template use File | Settings | File Templates.
 */
public class DataBase {
    private static final List<Item> ITEMS = new ArrayList<Item>();
    private static final Map<String, Schedule> schedules = new HashMap<String, Schedule>();
    private static final DataBase INSTANCE = new DataBase();

    static {
        schedules.put("casdevbr", createSchedule("devbr"));
        schedules.put("castestbr", createSchedule("tstbr"));
        schedules.put("casuatbr", createSchedule("uatbr"));


        ITEMS.add(new Item("casdevbr", "running", "dk.telia.cas.cas", "2.31.2", "casdevbr", "casdevbr.test.telia.dk", "/opt/casdevbr","casdevbr", "20.02.2011 14:55", "2.32.10", "2.33.30", "2.33-SNAPSHOT", false));
        ITEMS.add(new Item("castestbr", "running", "dk.telia.cas.cas", "2.31-SNAPSHOT", "castestbr", "castestbr.test.telia.dk", "/opt/castestbr","castestbr", "20.02.2011 14:55", "2.32.10", "2.33.30", "2.33-SNAPSHOT", true));
        ITEMS.add(new Item("casuatbr", "running", "dk.telia.cas.cas", "2.31.2", "casuatbr", "casuatbr.test.telia.dk", "/opt/casuatbr","casuatbr", "20.02.2011 14:55", "2.32.10", "2.33.30", "2.33-SNAPSHOT", false));
    }

    public static DataBase getInstance() {
        return INSTANCE;
    }

    public List<Item> getConfigItems() {
        return ITEMS;    
    }

    public Item getConfigItemByAppName(String appName) {
        for (Item item : ITEMS) {
            if (appName.equals(item.getApplicationName())) {
                return item;
            }
        }

        return null;
    }

    public Schedule getScheduleFor(String appName) {
        return schedules.get(appName);
    }

    public String[] getVersions(String artifact) {
        String[] versions;

        versions = new String[10];
        versions[0] = "2.32.4";
        versions[1] = "2.32.3";
        versions[2] = "2.32.2";
        versions[3] = "2.32.1";
        versions[4] = "2.32.0";
        versions[5] = "2.31.9";
        versions[6] = "2.31.8";
        versions[7] = "2.31.7";
        versions[8] = "2.31.6";
        versions[9] = "2.31.5";

        return versions;
    }

    public void persist(Schedule schedule, String applicationName) {

    }

    private static Schedule createSchedule(String job) {
        Schedule schedule;

        schedule = new Schedule();
        schedule.getJobs()[0].setExecute(job);

        return schedule;
    }
}
