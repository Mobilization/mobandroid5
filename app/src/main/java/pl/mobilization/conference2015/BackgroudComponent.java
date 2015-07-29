package pl.mobilization.conference2015;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import de.greenrobot.event.EventBus;
import pl.mobilization.conference2015.sponsor.SponsorModule;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestService;
import pl.mobilization.conference2015.sponsor.storage.SponsorStorage;

/**
 * Created by msaramak on 29.07.15.
 */
@Singleton
@Component(modules = {SponsorModule.class, EventBusModule.class})
public interface BackgroudComponent {
//    public SponsorStorage provideSponsorStorage(Context context);
    public SponsorRestService provideSponsorRestService();
    public EventBus provideEventBus();
}
