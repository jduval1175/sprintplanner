package jdu.sprintplanner.jobs.exceptions;

import org.quartz.JobExecutionException;

public class UnknownSprintException extends JobExecutionException {
    private final String id;

    public UnknownSprintException(String id) {
        super();
        this.id = id;
    }

}
