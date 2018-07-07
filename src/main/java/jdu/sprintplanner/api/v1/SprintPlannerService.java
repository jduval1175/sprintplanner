package jdu.sprintplanner.api.v1;

import jdu.sprintplanner.model.Role;
import jdu.sprintplanner.model.Sprint;
import jdu.sprintplanner.model.Teammate;
import jdu.sprintplanner.repositories.RoleRepository;
import jdu.sprintplanner.repositories.SprintRepository;
import jdu.sprintplanner.repositories.TeamMateRepository;
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
    private RoleRepository roleRepository;

    ///////////////////////////////////////////////
    // SPRINTS
    ////
    @GetMapping("/sprints/{id}")
    public Sprint retrieveSprint(@PathVariable long id) throws ResourceNotFoundException {
        return ofOptional(sprintRepository.findById(id))
                .getOrElseThrow(() -> new ResourceNotFoundException("Sprint-" + id));
    }

    @GetMapping("/sprints")
    public List<Sprint> retrieveAllSprints() {
        return sprintRepository.findAll();
    }

    @DeleteMapping("/sprints/{id}")
    public void deleteSprint(@PathVariable long id) {
        sprintRepository.deleteById(id);
    }

    @PostMapping("/sprints")
    public ResponseEntity<Object> createSprint(@RequestBody Sprint sprint) {
        Sprint savedSprint = sprintRepository.save(sprint);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedSprint.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/sprints/{id}")
    public ResponseEntity<Object> updateSprint(@RequestBody Sprint sprint, @PathVariable long id) {
        Optional<Sprint> sprintOriginal = sprintRepository.findById(id);
        if (!sprintOriginal.isPresent())
            return ResponseEntity.notFound().build();
        sprint.setId(id);
        sprintRepository.save(sprint);
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
        Optional<Teammate> sprintOriginal = teammateRepository.findById(id);
        if (!sprintOriginal.isPresent())
            return ResponseEntity.notFound().build();
        teammate.setId(id);
        teammateRepository.save(teammate);
        return ResponseEntity.noContent().build();
    }

    ///////////////////////////////////////////////
    // ROLES
    ////
    @GetMapping("/roles")
    public List<Role> retrieveAllRoles() {
        return roleRepository.findAll();
    }

    @GetMapping("/roles/{id}")
    public Role retrieveRole(@PathVariable long id) throws ResourceNotFoundException {
        return ofOptional(roleRepository.findById(id))
                .getOrElseThrow(() -> new ResourceNotFoundException("Role-" + id));
    }

    @DeleteMapping("/roles/{id}")
    public void deleteRole(@PathVariable long id) {
        roleRepository.deleteById(id);
    }

    @PostMapping("/roles")
    public ResponseEntity<Object> createRole(@RequestBody Role role) {
        Role savedRole = roleRepository.save(role);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedRole.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<Object> updateRole(@RequestBody Role role, @PathVariable long id) {
        Optional<Role> originalRole = roleRepository.findById(id);
        if (!originalRole.isPresent())
            return ResponseEntity.notFound().build();
        role.setId(id);
        roleRepository.save(role);
        return ResponseEntity.noContent().build();
    }

    private class ResourceNotFoundException extends Throwable {
        private final String id;
        public ResourceNotFoundException(String id) {
            this.id = id;
        }
    }

}
