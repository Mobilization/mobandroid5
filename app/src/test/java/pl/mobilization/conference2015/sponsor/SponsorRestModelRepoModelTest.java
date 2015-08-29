package pl.mobilization.conference2015.sponsor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import pl.mobilization.conference2015.BuildConfig;
import pl.mobilization.conference2015.sponsor.repository.SponsorRepoModel;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

/**
 * Created by mario on 16.08.15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SponsorRestModelRepoModelTest {

    public static final String SPONSOR_NAME = "name";
    public static final String SPONSOR_URL = "http://wwww.mobilca.com";
    public static final String SPONSOR_LOGO = "http://www.mobica.com/logo.png";
    public static final int SPONSOR_LEVEL = 1;

    @Test()
    public void shouldThrowExceptionWhenCreatingObjectWithEmptyName() {
        try {
            SponsorRepoModel.builder("").build();
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
            return;
        }
        fail("No expected exception");
    }

    @Test()
    public void shouldThrowExceptionWhenCreatingObjectWithEmptyUrl() {
        try {
            SponsorRepoModel.builder("name").url("").build();
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
            return;
        }
        fail("No expected exception");
    }

    @Test
    public void shouldThrowExceptionWhenCreatingObjectWithWrongUrl() {
        try {
            SponsorRepoModel.builder("name").url("http:\\www.wp.pl").build();
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
            return;
        }
        fail("No expected exception");
    }

    @Test
    public void shouldCorrectlyCreateSponsorModel() {
        //GIVEN values to create SponsorRepoModel

        SponsorRepoModel repo = SponsorRepoModel.builder(SPONSOR_NAME).url(SPONSOR_URL)
                .level(SPONSOR_LEVEL).logo(SPONSOR_LOGO)
                        //WHEN create
                .build();
        //THEN
        assertThat(repo.getName()).isEqualTo(SPONSOR_NAME);
        assertThat(repo.getLogo()).isEqualTo(SPONSOR_LOGO);
        assertThat(repo.getUrl()).isEqualTo(SPONSOR_URL);
        assertThat(repo.getLevel()).isEqualTo(SPONSOR_LEVEL);
    }
}
