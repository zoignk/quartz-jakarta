package com.airhacks;


import java.io.Serializable;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SchedulerListener.class This class initialize scheduler on application start
 *
 */
@WebListener
public class SchedulerListener implements ServletContextListener, Serializable {

    /**
     * System configuration updated event
     */
    @Inject
    private Event<SchedulerContextInitialisedEvent> schedulerContextInitialisedEvent;
    private static final Logger logger = LoggerFactory.getLogger(SchedulerListener.class);

    /**
     * Call schedulers on application start
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        logger.info("FIREEEEEEEEE");
        this.schedulerContextInitialisedEvent.fire(new SchedulerContextInitialisedEvent());
    }

    /**
     * Do nothing on context destroy
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}

