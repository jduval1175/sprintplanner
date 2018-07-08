package jdu.sprintplanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Register {
    @Id
    @GeneratedValue
    long id;
    @OneToMany
    Set<Teammate> release;
    @OneToMany
    Set<Teammate> scrum;
    @OneToMany
    Set<SupportTeam> support;
}
