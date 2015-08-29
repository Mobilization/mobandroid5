package pl.mobilization.conference2015.sponsor;

import com.google.common.collect.Iterables;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import pl.mobilization.conference2015.BuildConfig;
import pl.mobilization.conference2015.sponsor.repository.SponsorRepoModel;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestModel;
import pl.mobilization.conference2015.sponsor.rest.SponsorListRestModel;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by mario on 13.08.15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MapSponsorListRestModelRepoToSponsorRestModelPrezTest {

    public static final String DIAMOUND_TEST_NAME = "diamound";
    public static final String DIAMOUND_TEST_DESCRIPTION = "diamound description";
    public static final String DIAMOUND_TEST_LOGO_URL = "http://2015.mobilization.pl/images/partners/juglodz.png";
    public static final String DIAMOUND_TEST_LINK = "http://www.wp.pl";

    @Test
    public void shouldConvertRepoSponsorModelIntoPresentationModelEmptyCase1() throws Exception {
        //GIVEN a tested mapper and no sponsors read from repo
        MapSponsorsRepoToSponsorPrez tested = new MapSponsorsRepoToSponsorPrez();
        List<SponsorRepoModel> sponsorsRepo = new ArrayList<>();
        //WHEN convert sponsors
        SponsorListRestModel result = tested.call(sponsorsRepo);
        //THEN sponsors should be converted
        assertThat(result.diamond).isEmpty();
        assertThat(result.platinum).isEmpty();
        assertThat(result.gold).isEmpty();
        assertThat(result.silver).isEmpty();
    }

    @Test
    public void shouldConvertRepoSponsorModelIntoPresentationModelOneDiamountCase() {
        //GIVEN a tested mapper and few sponsors read from repo
        MapSponsorsRepoToSponsorPrez tested = new MapSponsorsRepoToSponsorPrez();
        List<SponsorRepoModel> sponsorsRepo = new ArrayList<>();
        sponsorsRepo.add(createTestDiamound());
        //WHEN convert sponsors
        SponsorListRestModel result = tested.call(sponsorsRepo);
        //THEN sponsors should be converted
        assertThat(result.diamond).isNotEmpty();
        SponsorRestModel first = Iterables.getFirst(result.diamond, null);
        assertThat(first.name).isEqualTo(DIAMOUND_TEST_NAME);
        assertThat(first.logo_url).isEqualTo(DIAMOUND_TEST_LOGO_URL);
        assertThat(first.description_html).isEqualTo(DIAMOUND_TEST_DESCRIPTION);
        assertThat(first.link).isEqualTo(DIAMOUND_TEST_LINK);

        assertThat(result.platinum).isEmpty();
        assertThat(result.gold).isEmpty();
        assertThat(result.silver).isEmpty();
    }

    private SponsorRepoModel createTestDiamound() {
        SponsorRepoModel diamond = SponsorRepoModel.builder(DIAMOUND_TEST_NAME).description(DIAMOUND_TEST_DESCRIPTION)
                .level(0).logo(DIAMOUND_TEST_LOGO_URL).url(DIAMOUND_TEST_LINK).build();

        return diamond;
    }
}