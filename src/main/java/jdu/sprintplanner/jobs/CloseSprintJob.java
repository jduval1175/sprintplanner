package jdu.sprintplanner.jobs;

import jdu.sprintplanner.jobs.exceptions.UnknownSprintException;
import jdu.sprintplanner.model.Sprint;
import jdu.sprintplanner.repositories.SprintRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Slf4j
public class CloseSprintJob implements Job {
    public static final int SPRINT_SIZE = 14;
    @Autowired
    private SprintRepository sprintRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Closing Sprint");
        JobDataMap map = context.getMergedJobDataMap();

        Long id = map.getLong("id");
        Sprint sprint = sprintRepository.findById(id).orElseThrow(() -> new UnknownSprintException(id.toString()));

        LocalDate nextStartDate = sprint.getEndDate().plusDays(1);
        LocalDate nextEndDate = nextStartDate.plusDays(SPRINT_SIZE - 1);

        Sprint nextSprint = new Sprint();
        nextSprint.setStartDate(nextStartDate);
        nextSprint.setEndDate(nextEndDate);

        log.info("Closed : " + sprint);
        log.info("Next Sprint : " + nextSprint);

        //sendEmail(map);
        log.info("Sprint Closed");

    }
}
