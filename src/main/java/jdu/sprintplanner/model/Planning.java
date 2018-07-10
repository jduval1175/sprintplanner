package jdu.sprintplanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Planning implements Serializable{
    @Id
    long rank;
    @Id
    String role;
    @OneToOne
    Teammate teammate;
    //TODO Replace SupportTeam with OneToMany Teammate relationships, no more teammate/supportteam stuff ?
    @OneToOne
    SupportTeam support;
}
