package pl.mobilization.conference2015.inject;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by msaramak on 31.07.15.
 */
@Module
public class SchedulersModule {

    @Provides
    @Singleton
    @Named("main")
    public Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }


    @Provides
    @Singleton
    @Named("internet")
    public Scheduler provideInternetScheduler() {
        return Schedulers.io();
    }
}
