package pl.mobilization.conference2015.sponsor;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestService;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestServiceRetrofit;
import pl.mobilization.conference2015.sponsor.storage.SponsorStorage;
import pl.mobilization.conference2015.sponsor.storage.SponsorStorageOrmLite;

/**
 * Created by msaramak on 29.07.15.
 */
@Module
public class SponsorModule {

    @Provides @Singleton
    public SponsorRestService provideSponsorService(){
        return new SponsorRestServiceRetrofit();
    }


    @Provides @Singleton
    public SponsorStorage provideSponsorStorage(Context context){
        return new SponsorStorageOrmLite(context);
    }

    @Provides @Singleton
    public SponsorPresenter provideSponsorPresenter(){
        return new SponsorPresenter();
    }



}
