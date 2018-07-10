package jdu.sprintplanner.service;

import jdu.sprintplanner.model.Register;
import jdu.sprintplanner.model.SupportTeam;
import jdu.sprintplanner.model.Teammate;
import jdu.sprintplanner.repositories.RegisterRepository;
import jdu.sprintplanner.repositories.SupportTeamRepository;
import jdu.sprintplanner.repositories.TeammateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;
    @Autowired
    private TeammateRepository teammateRepository;
    @Autowired
    private SupportTeamRepository supportRepository;


    public void registerRelease(final Teammate releaser)
    {
        if (releaser != null) {
            Register register = getOrCreateRegister();
            register.getRelease().add(releaser);
            registerRepository.save(register);
        }
    }

    public void registerScrum(final Teammate scrum)
    {
        if (scrum != null) {
            Register register = getOrCreateRegister();
            register.getScrum().add(scrum);
            registerRepository.save(register);
        }
    }

    private Register getOrCreateRegister() {
        return registerRepository.findById(1L)
                    .orElse(createRegister());
    }

    public void registerSupport(final SupportTeam supportTeam)
    {
        if (supportTeam != null) {
            Register register = getOrCreateRegister();
            register.getSupport().add(supportTeam);
            registerRepository.save(register);
        }
    }

    public Teammate getNextReleaser() {
        List<Teammate> teammates = teammateRepository.findAll();
        Register register = getOrCreateRegister();

        List<Teammate> candidates = new ArrayList<>(teammates);
        candidates.removeAll(register.getRelease());
        if (candidates.isEmpty()) {
            register.getRelease().clear();
            registerRepository.save(register);
            return teammates.get(0);
        }
        else
        {
            return candidates.get(0);
        }
    }

    public Teammate getNextScrum() {
        List<Teammate> teammates = teammateRepository.findAll();
        Register register = getOrCreateRegister();

        List<Teammate> candidates = new ArrayList<>(teammates);
        candidates.removeAll(register.getScrum());
        if (candidates.isEmpty()) {
            register.getScrum().clear();
            registerRepository.save(register);
            return teammates.get(0);
        }
        else
        {
            return candidates.get(0);
        }
    }

    public SupportTeam getNextSupport() {
        List<SupportTeam> supportTeams = supportRepository.findAll();
        Register register = getOrCreateRegister();

        List<SupportTeam> candidates = new ArrayList<>(supportTeams);
        candidates.removeAll(register.getSupport());
        if (candidates.isEmpty()) {
            register.getRelease().clear();
            registerRepository.save(register);
            return supportTeams.get(0);
        }
        else
        {
            return candidates.get(0);
        }
    }

    private Register createRegister() {
        Register register = new Register();
        register.setId(1L);
        return register;
    }

}
