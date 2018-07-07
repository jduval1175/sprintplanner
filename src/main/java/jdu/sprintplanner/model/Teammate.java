package jdu.sprintplanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TEAMMATE")
@Getter
@Setter
public class Teammate {
    @Id
    @GeneratedValue
    private long id;
    private String trigram;
    private String lastname;
    private String firstname;
    private String mail;
}
