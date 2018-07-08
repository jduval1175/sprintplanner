package jdu.sprintplanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class SupportTeam {
    @Id
    @GeneratedValue
    private long id;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Teammate> members;
}
