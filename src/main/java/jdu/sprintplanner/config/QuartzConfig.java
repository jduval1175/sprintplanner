package jdu.sprintplanner.config;

import jdu.sprintplanner.quartz.AutowiringSpringBeanJobFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactory() {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();

        return factoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactory(ApplicationContext applicationContext, DataSource dataSource) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);

        schedulerFactoryBean.setJobFactory(jobFactory);    // Set jobFactory to AutowiringSpringBeanJobFactory
        return schedulerFactoryBean;
    }

}
