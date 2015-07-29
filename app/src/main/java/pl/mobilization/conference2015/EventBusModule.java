package pl.mobilization.conference2015;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

/**
 * Created by msaramak on 29.07.15.
 */
@Module
public class EventBusModule {

    @Provides @Singleton
    public EventBus provideEventBus(){
        return EventBus.getDefault();
    }

}
