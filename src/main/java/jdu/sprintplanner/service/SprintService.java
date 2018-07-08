package jdu.sprintplanner.service;

import io.vavr.control.Option;
import jdu.sprintplanner.model.Sprint;
import jdu.sprintplanner.repositories.SprintRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static io.vavr.control.Option.none;
import static io.vavr.control.Option.ofOptional;
import static io.vavr.control.Option.some;

@Slf4j
@Service
public class SprintService {

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
        return savedSprint;
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
}
