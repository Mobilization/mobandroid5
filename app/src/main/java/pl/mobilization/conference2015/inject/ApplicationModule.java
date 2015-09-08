package pl.mobilization.conference2015.inject;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import pl.mobilization.conference2015.android.AndroidApplication;
import pl.mobilization.conference2015.sponsor.SponsorPresenter;
import pl.mobilization.conference2015.sponsor.repository.SponsorRepository;
import pl.mobilization.conference2015.sponsor.repository.SponsorRepositoryOrmLite;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestService;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestServiceRetrofit;

/**
 * Created by msaramak on 29.07.15.
 */
@Module(includes = {SchedulersModule.class})
public class ApplicationModule {

    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }
    
    @Provides
    @Singleton
    EventBus provideEventBus(){
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    public SponsorRestService provideSponsorRestService() {
        return new SponsorRestServiceRetrofit();
    }


    @Provides
    @Singleton
    public SponsorRepository provideSponsorRepository(Context context) {
        return new SponsorRepositoryOrmLite(context);
    }

    @Provides
    @Singleton
    public SponsorPresenter provideSponsorPresenter(SponsorRepository repository, EventBus eventBus) {
        return new SponsorPresenter(repository, eventBus);
    }


}
