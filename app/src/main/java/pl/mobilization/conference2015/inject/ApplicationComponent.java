package pl.mobilization.conference2015.inject;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import pl.mobilization.conference2015.android.BackgroundProcessService;
import pl.mobilization.conference2015.android.BaseActivity;
import pl.mobilization.conference2015.sponsor.SponsorPresenter;

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
