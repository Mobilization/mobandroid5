package pl.mobilization.conference2015.domain.executor;

import rx.Scheduler;

/**
 * Created by msaramak on 29.07.15.
 */
public interface PostExecutionThread {
    Scheduler getScheduler();
}