package pl.mobilization.conference2015;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import de.greenrobot.event.EventBus;
import pl.mobilization.conference2015.sponsor.SponsorPresenter;
import pl.mobilization.conference2015.sponsor.SponsorsFragment;
import pl.mobilization.conference2015.sponsor.repository.SponsorRepository;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestService;

/**
 * Created by msaramak on 29.07.15.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    //Exposed to sub-graphs.
    Context context();

    SponsorPresenter sponsorPresenter();



    void inject(BaseActivity baseActivity);
    void inject(BackgroundProcessService bps);
    void inject(SponsorPresenter pr);

}
