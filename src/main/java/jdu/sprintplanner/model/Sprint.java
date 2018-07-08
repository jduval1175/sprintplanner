package jdu.sprintplanner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table
@Getter
@Setter
@ToString
public class Sprint {

    @Id
    @GeneratedValue
    long id;
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;

    @OneToOne
    private SupportTeam support;
    @OneToOne
    private Teammate releaser;
    @OneToOne
    private Teammate scrumMaster;
}
