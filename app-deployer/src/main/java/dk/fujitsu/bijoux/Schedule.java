package dk.fujitsu.bijoux;

import javax.print.attribute.standard.JobSheets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 05-04-2011
 * Time: 20:58:50
 * To change this template use File | Settings | File Templates.
 */
public class Schedule {
    private static final int JOBS = 5;
    private Job[] jobs;

    public Schedule() {
        jobs = new Job[JOBS];

        for (int i = 0; i < JOBS; i++) {
            jobs[i] = new Job();
        }
    }

    public Job[] getJobs() {
        return jobs;
    }

    public void setJobs(Job[] jobs) {
        this.jobs = jobs;
    }

    public Schedule clone() {
        Schedule clone;

        clone = new Schedule();
        for (int i = 0; i < JOBS; i++) {
            clone.jobs[i] = jobs[i].clone();
        }

        return clone;
    }
}
