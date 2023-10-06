package com.airhacks;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class SimpleScheduler {
    @Inject
    CdiJobFactory jobFactory;
    private Scheduler simpleScheduler;

    private static final Logger logger = LoggerFactory.getLogger(SimpleScheduler.class);

    public void scheduleJobs(@Observes SchedulerContextInitialisedEvent event) {

        if (this.simpleScheduler != null) {
            try {
                this.simpleScheduler.shutdown(true);
            } catch (SchedulerException var2) {
                logger.error("Exception while stopping jobs.", var2);
            }
        }

        logger.info("AAAAAAAAAAAAAAAAAAAAA: " + event.toString());

        JobKey jobKey = new JobKey("quartz", "quartz-job");

        try {

            this.simpleScheduler = StdSchedulerFactory.getDefaultScheduler();
            this.simpleScheduler.setJobFactory(this.jobFactory);


            logger.info("Started SCHEDULEEEEEEEEEEEER: " + this.simpleScheduler.getSchedulerInstanceId());
            if (this.simpleScheduler.checkExists(jobKey)) {
                logger.info("It exists");
                this.simpleScheduler.deleteJob(jobKey);
            }
            this.simpleScheduler.start();
            JobDetail jobDetail = JobBuilder
                    .newJob(TestJob.class)
                    .storeDurably()
                    .withIdentity("quartz", "quartz-job")
                    .withDescription("test job")
                    .build();

            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("SimpleJob")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(10)
                            .repeatForever())
                    .build();

            this.simpleScheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException var4) {
            logger.info("Exception while unscheduling job: quartz" +  var4.getMessage());
        }
    }

    public static void main(String[] args) {
//            if (simpleScheduler != null) {
//                try {
//                    simpleScheduler.shutdown(true);
//                } catch (SchedulerException var2) {
//                    logger.error("Exception while stopping jobs.", var2);
//                }
//            }

        System.out.println("START");

            JobKey jobKey = new JobKey("quartz-main", "quartz-job");

            try {

                Scheduler simpleScheduler = StdSchedulerFactory.getDefaultScheduler();
//                simpleScheduler.setJobFactory(jobFactory);
                simpleScheduler.start();

                System.out.println("Started SCHEDULEEEEEEEEEEEER: " + simpleScheduler.getSchedulerInstanceId());
                if (simpleScheduler.checkExists(jobKey)) {
                    simpleScheduler.deleteJob(jobKey);
                }
                JobDetail jobDetail = JobBuilder
                        .newJob(TestJob.class)
                        .storeDurably()
                        .withIdentity("quartz-main", "quartz-job")
                        .withDescription("test job")
                        .build();

                Trigger trigger = TriggerBuilder
                        .newTrigger()
                        .withIdentity("SimpleJob-trigger")
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(10)
                                .repeatForever())
                        .build();

                simpleScheduler.scheduleJob(jobDetail, trigger);

            } catch (SchedulerException var4) {
                System.out.println("Exception while unscheduling job: quartz" +  var4.getMessage());
            }
        
    }

    @PostConstruct
    private void postConstruct() {
        logger.info("INIIIIIIIIIIIT");
    }
}
