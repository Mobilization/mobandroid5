package pl.mobilization.conference2015.sponsor.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;


import dagger.Component;
import dagger.Module;
import pl.mobilization.conference2015.BuildConfig;
import pl.mobilization.conference2015.sponsor.SponsorModel;
import rx.Observable;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by msaramak on 29.07.15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SponsorRestServiceTest {


    @Test
    public void downloadSponsorsTest(){
        SponsorRestService service = new SponsorRestServiceRetrofit();
        Observable<Sponsors> osponsors = service.getSponsors();
        Sponsors sponsors = osponsors.toBlocking().first();
        assertThat(sponsors).isNotNull();
        assertThat(sponsors.platinum).isNotEmpty();
        assertThat(sponsors.platinum.get(0).name).isNotEmpty();
        assertThat(service).isNotNull();
    }

}
