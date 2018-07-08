package jdu.sprintplanner.api.v1;

import jdu.sprintplanner.model.Register;
import jdu.sprintplanner.model.Sprint;
import jdu.sprintplanner.model.SupportTeam;
import jdu.sprintplanner.model.Teammate;
import jdu.sprintplanner.repositories.RegisterRepository;
import jdu.sprintplanner.repositories.SprintRepository;
import jdu.sprintplanner.repositories.SupportTeamRepository;
import jdu.sprintplanner.repositories.TeamMateRepository;
import jdu.sprintplanner.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static io.vavr.control.Option.ofOptional;

@RestController
public class SprintPlannerService {
    final String API_ROOT = "";
    private Logger log = Logger.getLogger(SprintPlannerService.class.getName());


    @Autowired
    private SprintRepository sprintRepository;
    @Autowired
    private TeamMateRepository teammateRepository;
    @Autowired
    private SupportTeamRepository supportRepository;
    @Autowired
    private RegisterRepository registerRepository;
    @Autowired
    private SprintService sprintService;

    ///////////////////////////////////////////////
    // SPRINTS
    ////
    @GetMapping("/sprints/{id}")
    public Sprint retrieveSprint(@PathVariable long id) throws ResourceNotFoundException {
        return sprintService.findSprint(id)
                .getOrElseThrow(() -> new ResourceNotFoundException("Sprint-" + id));
    }

    @GetMapping("/sprints")
    public List<Sprint> retrieveAllSprints() {
        return sprintService.findAll();
    }

    @GetMapping("/sprints/current")
    public Sprint retrieveCurrentSprint() throws ResourceNotFoundException {
        return sprintService.findCurrent()
                .getOrElseThrow(() -> new ResourceNotFoundException("Sprint-Current"));
    }

    @DeleteMapping("/sprints/{id}")
    public void deleteSprint(@PathVariable long id) {
        sprintService.deleteSprint(id);
    }

    @PostMapping("/sprints")
    public ResponseEntity<Object> createSprint(@RequestBody Sprint sprint) {
        Sprint savedSprint = sprintService.createSprint(sprint);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedSprint.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/sprints/{id}")
    public ResponseEntity<Object> updateSprint(@RequestBody Sprint sprint, @PathVariable long id) {
        return sprintService.updateSprint(sprint, id)
                .map(s -> ResponseEntity.noContent().build())
                .getOrElse(ResponseEntity.notFound().build());
    }

    @PatchMapping(path = "/sprints/{id}/pause")
    public ResponseEntity<Void> pauseJob(@PathVariable long id) {
        sprintService.pauseSprint(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/sprints/{id}/resume")
    public ResponseEntity<Void> resumeJob(@PathVariable long id) {
        sprintService.resumeSprint(id);
        return ResponseEntity.noContent().build();
    }
    ///////////////////////////////////////////////
    // TEAMMATES
    ////
    @GetMapping("/teammates")
    public List<Teammate> retrieveAllTeamMates() {
        return teammateRepository.findAll();
    }

    @GetMapping("/teammates/{id}")
    public Teammate retrieveTeammate(@PathVariable long id) throws ResourceNotFoundException {
        return ofOptional(teammateRepository.findById(id))
                .getOrElseThrow(() -> new ResourceNotFoundException("Teammate-" + id));
    }

    @DeleteMapping("/teammates/{id}")
    public void deleteTeammate(@PathVariable long id) {
        teammateRepository.deleteById(id);
    }

    @PostMapping("/teammates")
    public ResponseEntity<Object> createTeammate(@RequestBody Teammate teammate) {
        Teammate savedTeammate = teammateRepository.save(teammate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedTeammate.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/teammates/{id}")
    public ResponseEntity<Object> updateTeammate(@RequestBody Teammate teammate, @PathVariable long id) {
        Optional<Teammate> oldTeammate = teammateRepository.findById(id);
        if (!oldTeammate.isPresent())
            return ResponseEntity.notFound().build();
        teammate.setId(id);
        teammateRepository.save(teammate);
        return ResponseEntity.noContent().build();
    }

    ///////////////////////////////////////////////
    // SUPPORT
    ////
    @GetMapping("/supports")
    public List<SupportTeam> retrieveAllSupports() {
        return supportRepository.findAll();
    }

    @GetMapping("/supports/{id}")
    public SupportTeam retrieveSupport(@PathVariable long id) throws ResourceNotFoundException {
        return ofOptional(supportRepository.findById(id))
                .getOrElseThrow(() -> new ResourceNotFoundException("Support-" + id));
    }

    @DeleteMapping("/supports/{id}")
    public void deleteSupport(@PathVariable long id) {
        supportRepository.deleteById(id);
    }

    @PostMapping("/supports")
    public ResponseEntity<Object> createSupport(@RequestBody SupportTeam support) {
        SupportTeam savedSupport = supportRepository.save(support);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedSupport.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/supports/{id}")
    public ResponseEntity<Object> updateSupport(@RequestBody SupportTeam support, @PathVariable long id) {
        Optional<SupportTeam> oldSupport = supportRepository.findById(id);
        if (!oldSupport.isPresent())
            return ResponseEntity.notFound().build();
        support.setId(id);
        supportRepository.save(support);
        return ResponseEntity.noContent().build();
    }

    ///////////////////////////////////////////////
    // SUPPORT
    ////
    @GetMapping("/register")
    public List<Register> retrieveAllRegisters() {
        return registerRepository.findAll();
    }

    @GetMapping("/register/{id}")
    public Register retrieveRegister(@PathVariable long id) throws ResourceNotFoundException {
        return ofOptional(registerRepository.findById(id))
                .getOrElseThrow(() -> new ResourceNotFoundException("Register-" + id));
    }

    @DeleteMapping("/register/{id}")
    public void deleteRegister(@PathVariable long id) {
        registerRepository.deleteById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createRegister(@RequestBody Register register) {
        Register savedRegister = registerRepository.save(register);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedRegister.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/register/{id}")
    public ResponseEntity<Object> updateRegister(@RequestBody Register register, @PathVariable long id) {
        Optional<Register> oldRegister = registerRepository.findById(id);
        if (!oldRegister.isPresent())
            return ResponseEntity.notFound().build();
        register.setId(id);
        registerRepository.save(register);
        return ResponseEntity.noContent().build();
    }
    
    ///////////////////////////////////////////////
    ///////////////////////////////////////////////
    // Exceptions
    ////

    private class ResourceNotFoundException extends Throwable {
        private final String id;
        public ResourceNotFoundException(String id) {
            this.id = id;
        }
    }

}
