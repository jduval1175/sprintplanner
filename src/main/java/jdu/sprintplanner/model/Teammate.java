package jdu.sprintplanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @Override
    public String toString() {
        return "Teammate{" +
                "id=" + id +
                ", trigram='" + trigram + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teammate teammate = (Teammate) o;

        return id == teammate.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
