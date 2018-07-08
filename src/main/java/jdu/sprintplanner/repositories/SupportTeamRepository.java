package jdu.sprintplanner.repositories;

import jdu.sprintplanner.model.SupportTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupportTeamRepository extends JpaRepository<SupportTeam, Long> {
}

