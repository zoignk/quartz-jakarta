package com.airhacks;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("call")
public class CallResource {
    @Inject
    private Event<SchedulerContextInitialisedEvent> schedulerContextInitialisedEvent;
    @GET
    public String call() {
        this.schedulerContextInitialisedEvent.fire(new SchedulerContextInitialisedEvent());
        return "called";
    }
}
