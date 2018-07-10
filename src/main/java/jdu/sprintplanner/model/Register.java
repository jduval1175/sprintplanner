package jdu.sprintplanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Register {
    @Id
    long id;
    @OneToMany(fetch = FetchType.EAGER)
    Set<Teammate> release;
    @OneToMany(fetch = FetchType.EAGER)
    Set<Teammate> scrum;
    @OneToMany(fetch = FetchType.EAGER)
    Set<SupportTeam> support;

    public Register() {
        id = 1;
        release = new HashSet<>();
        scrum = new HashSet<>();
        support = new HashSet<>();
    }
}
