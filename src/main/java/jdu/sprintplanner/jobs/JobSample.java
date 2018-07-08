package jdu.sprintplanner.jobs;

import jdu.sprintplanner.model.Sprint;
import jdu.sprintplanner.repositories.SprintRepository;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class JobSample extends QuartzJobBean {

    @Autowired
    SprintRepository sprintRepository;

    private String name;

    // Inject the "name" job data property
    public void setName(String name) { this.name = name; }

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        List<Sprint> all = sprintRepository.findAll();

        all.forEach(System.out::println);

    }

}
