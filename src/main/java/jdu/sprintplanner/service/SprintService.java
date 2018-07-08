package jdu.sprintplanner.service;

import io.vavr.Tuple2;
import io.vavr.control.Option;
import jdu.sprintplanner.jobs.CloseSprintJob;
import jdu.sprintplanner.jobs.EmailJob;
import jdu.sprintplanner.jobs.OpenSprintJob;
import jdu.sprintplanner.model.Sprint;
import jdu.sprintplanner.repositories.SprintRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static io.vavr.control.Option.none;
import static io.vavr.control.Option.ofOptional;
import static io.vavr.control.Option.some;
import static java.time.ZoneId.systemDefault;
import static jdu.sprintplanner.service.Utils.*;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SprintService {
    private static final String CLOSE_SPRINT = "CloseSprint";
    private final Scheduler scheduler;

    public static final String OPEN_SPRINT = "OpenSprint";
    @Autowired
    private SprintRepository sprintRepository;

    public Option<Sprint> findSprint(long id) {
        return ofOptional(sprintRepository.findById(id));
    }

    public List<Sprint> findAll()
    {
        return sprintRepository.findAll();
    }

    public void deleteSprint(long id) {
        sprintRepository.deleteById(id);
    }

    public Sprint createSprint(Sprint sprint) {
        Sprint savedSprint = sprintRepository.save(sprint);

        createJob(buildTaskOpenSprint(savedSprint));
        createJob(buildTaskCloseSprint(savedSprint));

        return savedSprint;
    }


    private void createJob(Tuple2<Trigger,JobDetail> task) {
        createJob(task._1(),task._2());
    }

    private void createJob(Trigger trigger, JobDetail jobDetail) {
        log.info("About to save job with key - {}", jobDetail.getKey());
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("Job with key - {} saved sucessfully", jobDetail.getKey());
        } catch (SchedulerException e) {
            log.error("Could not save job with key - {} due to error - {}", jobDetail.getKey(), e.getLocalizedMessage());
            throw new IllegalArgumentException(e.getLocalizedMessage());
        }
    }

    private String getQuartzGroup(Sprint sprint) {
        return "Sprint-" + sprint.getId();
    }

    private Trigger buildTrigger(String name, Sprint sprint, LocalDateTime firetime) {
        String group = getQuartzGroup(sprint);
        JobDataMap jobDataMap = new JobDataMap();
        return newTrigger()
                .withIdentity(name, group)
                .withSchedule(simpleSchedule()
                        .withMisfireHandlingInstructionNextWithExistingCount())
                .startAt(Date.from(firetime.atZone(getZone()).toInstant()))
                .usingJobData(jobDataMap)
                .build();
    }

    public Option<Sprint> updateSprint(Sprint sprint, long id) {
        //TODO Refacto, more java 8 style
        Optional<Sprint> sprintOriginal = sprintRepository.findById(id);
        if (!sprintOriginal.isPresent())
            return none();
        sprint.setId(id);
        sprintRepository.save(sprint);
        return some(sprint);
    }

    public Option<Sprint> findCurrent() {
        return none();
    }

    public void pauseSprint(long id) {

    }

    public void resumeSprint(long id) {

    }

    ////////////////////////////////////
    // TASKS
    ////////////

    private JobDetail buildJob(String jobName, Sprint sprint, Class clazz) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("id",sprint.getId());
        return newJob(clazz)
                .withIdentity(jobName, getQuartzGroup(sprint))
                .usingJobData(jobDataMap)
                .build();
    }
    /////////

    private Tuple2<Trigger,JobDetail> buildTaskOpenSprint(Sprint sprint) {
        Trigger trigger = buildTriggerOpenSprint(sprint);
        JobDetail jobDetail = buildJob(OPEN_SPRINT, sprint, OpenSprintJob.class);//buildJobOpenSprint(sprint);
        return new Tuple2(trigger,jobDetail);
    }

    private JobDetail buildJobOpenSprint(Sprint sprint) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("id",sprint.getId());
        return newJob(OpenSprintJob.class)
                .withIdentity(OPEN_SPRINT, getQuartzGroup(sprint))
                .usingJobData(jobDataMap)
                .build();
    }

    private Trigger buildTriggerOpenSprint(Sprint sprint) {
        LocalDate fireDay = sprint.getStartDate();
        LocalTime fireTime = LocalTime.NOON;
        LocalDateTime fire = LocalDateTime.of(fireDay,fireTime);
        //TODO Remove debug code
        LocalDateTime fireNext = LocalDateTime.now().plusSeconds(15);

        return buildTrigger(OPEN_SPRINT,sprint, fireNext);
    }

    ////////

    private Tuple2<Trigger,JobDetail> buildTaskCloseSprint(Sprint sprint) {
        Trigger trigger = buildTriggerCloseSprint(sprint);
        JobDetail jobDetail = buildJob(CLOSE_SPRINT, sprint, CloseSprintJob.class);//buildJobCloseSprint(sprint);
        return new Tuple2(trigger,jobDetail);
    }

    private JobDetail buildJobCloseSprint(Sprint sprint) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("id",sprint.getId());
        return newJob(CloseSprintJob.class)//EmailJob
                .withIdentity(CLOSE_SPRINT, getQuartzGroup(sprint))
                .usingJobData(jobDataMap)
                .build();
    }

    private Trigger buildTriggerCloseSprint(Sprint sprint) {
        LocalDate fireDay = sprint.getEndDate();
        LocalTime fireTime = LocalTime.of(23,59,59);
        LocalDateTime fire = LocalDateTime.of(fireDay,fireTime);
        //TODO Remove debug code
        LocalDateTime fireNext = LocalDateTime.now().plusSeconds(30);

        return buildTrigger(CLOSE_SPRINT,sprint, fireNext);
    }


}
