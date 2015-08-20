package pl.mobilization.conference2015.sponsor;

import javax.inject.Singleton;

import dagger.Component;
import de.greenrobot.event.EventBus;

/**
 * Created by msaramak on 19.08.15.
 */
@Singleton
@Component(modules = TestModule.class)
public interface TestComponent {
    void inject(SponsorPresenter sponsorPresenter);
}
