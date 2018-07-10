package jdu.sprintplanner.jobs;

import jdu.sprintplanner.jobs.exceptions.UnknownSprintException;
import jdu.sprintplanner.model.Sprint;
import jdu.sprintplanner.model.Teammate;
import jdu.sprintplanner.repositories.RegisterRepository;
import jdu.sprintplanner.repositories.SprintRepository;
import jdu.sprintplanner.service.RegisterService;
import jdu.sprintplanner.service.SprintService;
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
    private SprintService sprintService;
    @Autowired
    private RegisterService registerService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Closing Sprint");
        JobDataMap map = context.getMergedJobDataMap();

        Long id = map.getLong("id");
        Sprint sprint = sprintService.findSprint(id).getOrElseThrow(() -> new UnknownSprintException(id.toString()));
        registerService.registerRelease(sprint.getReleaser());
        registerService.registerScrum(sprint.getScrum());
        registerService.registerSupport(sprint.getSupport());

        // Create new Sprint
        LocalDate nextStartDate = sprint.getEndDate().plusDays(1);
        LocalDate nextEndDate = nextStartDate.plusDays(SPRINT_SIZE - 1);

        Sprint nextSprint = new Sprint();
        nextSprint.setStartDate(nextStartDate);
        nextSprint.setEndDate(nextEndDate);
        nextSprint.setReleaser(registerService.getNextReleaser());
        nextSprint.setSupport(registerService.getNextSupport());
        nextSprint.setScrum(registerService.getNextScrum());

        sprintService.createSprint(nextSprint);

        log.info("Closed : " + sprint);
        log.info("Next Sprint : " + nextSprint);

        //sendEmail(map);
        log.info("Sprint Closed");

    }
}
