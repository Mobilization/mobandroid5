package pl.mobilization.conference2015;

import javax.inject.Inject;
import javax.inject.Singleton;

import pl.mobilization.conference2015.domain.executor.PostExecutionThread;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by msaramak on 29.07.15.
 */
@Singleton
public class UIThread implements PostExecutionThread {
    @Inject
    public UIThread() {}

    @Override public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}