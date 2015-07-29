package pl.mobilization.conference2015.sponsor;

import javax.inject.Singleton;

import dagger.Component;
import pl.mobilization.conference2015.EventBusModule;

/**
 * Created by msaramak on 29.07.15.
 */
@Singleton
@Component(modules = {SponsorModule.class, EventBusModule.class})
public interface SponsorComponent {

    public SponsorPresenter provideSponsorPresenter();
}
