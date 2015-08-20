package pl.mobilization.conference2015.sponsor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

/**
 * Created by msaramak on 19.08.15.
 */
@Module
public class TestModule {

    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return EventBus.getDefault();
    }
}
