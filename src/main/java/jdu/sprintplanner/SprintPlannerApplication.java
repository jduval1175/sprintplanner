package jdu.sprintplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = {SprintRepository.class})
public class SprintPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprintPlannerApplication.class, args);
	}
}
