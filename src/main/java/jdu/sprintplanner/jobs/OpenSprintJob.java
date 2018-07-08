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
import org.springframework.mail.javamail.JavaMailSender;


@Slf4j
public class OpenSprintJob implements Job {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SprintRepository sprintRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Opening Sprint");
        JobDataMap map = context.getMergedJobDataMap();

        Long id = map.getLong("id");
        Sprint sprint = sprintRepository.findById(id).orElseThrow(() -> new UnknownSprintException(id.toString()));

        log.info("Mail To Team : " + sprint);

        //sendEmail(map);
        log.info("Sprint Opened");
    }

}
