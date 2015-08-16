package pl.mobilization.conference2015.sponsor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import pl.mobilization.conference2015.BuildConfig;

/**
 * Created by mario on 16.08.15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SponsorRepoModelTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCreatingObjectWithEmptyName(){
        SponsorRepoModel.builder("").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCreatingObjectWithEmptyUrl(){
        SponsorRepoModel.builder("name").url("").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCreatingObjectWithWrongUrl(){
        SponsorRepoModel.builder("name").url("http:\\www.wp.pl").build();
    }
}
