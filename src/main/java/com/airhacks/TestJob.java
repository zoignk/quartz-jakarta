package com.airhacks;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class TestJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(TestJob.class);

    @Override
    public void execute(final JobExecutionContext ctx)
             {

        logger.info("EXECUUUUUUUUUUUUUUUUUUUUUTEEEEEEEEEEEEEEEEEEE");
        logger.info(ctx.getJobInstance().toString() +" " +ctx.getJobDetail() + " ");

    }

}