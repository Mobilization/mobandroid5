package pl.mobilization.conference2015.sponsor.repository;

import android.test.suitebuilder.annotation.MediumTest;

import com.google.common.collect.Iterables;
import com.j256.ormlite.support.ConnectionSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import pl.mobilization.conference2015.BuildConfig;
import rx.Observable;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by mario on 14.08.15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SponsorRestModelRepositoryOrmLiteTest {

    public static final String SPONSOR_NAME = "sponsorName";
    public static final String SPONSOR_DESCRIPTION_HTML = "<p>description html</p>";
    public static final int SPONSOR_DIAMOUND_LEVEL = 0;
    public static final String SPONSOR_URL = "http://www.mobica.com";
    public static final String SPONSOR_LOGO = "http://2015.mobilization.pl/images/sponsors/mobica.png";

    @Test
    @MediumTest
    public void shouldSaveSponsorListInDB(){
        //GIVEN sponsor repository and sponsor list to save
        SponsorRepository srtested = new SponsorRepositoryOrmLite(RuntimeEnvironment.application);
        List<SponsorRepoModel> sponsorsToSave = new ArrayList<>();
        SponsorRepoModel srm = SponsorRepoModel.builder(SPONSOR_NAME)
                .description(SPONSOR_DESCRIPTION_HTML)
                .level(SPONSOR_DIAMOUND_LEVEL)
                .logo(SPONSOR_LOGO)
                .level(SPONSOR_DIAMOUND_LEVEL)
                .url(SPONSOR_URL)
                .build();


        sponsorsToSave.add(srm);
        //WHEN save and read sponsor list
        srtested.saveSponsors(sponsorsToSave);
        Observable<List<SponsorRepoModel>> sponsorListFromDB = srtested.getSponsors();
        List<SponsorRepoModel> firstSponsorList = sponsorListFromDB.toBlocking().first();
        SponsorRepoModel firstSponsor = Iterables.getFirst(firstSponsorList, SponsorRepoModel.EMPTY);
        assertThat(firstSponsor.getName()).isEqualTo(SPONSOR_NAME);
        assertThat(firstSponsor.getDescriptionHtml()).isEqualTo(SPONSOR_DESCRIPTION_HTML);
        assertThat(firstSponsor.getLevel()).isEqualTo(SPONSOR_DIAMOUND_LEVEL);
        assertThat(firstSponsor.getLogo()).isEqualTo(SPONSOR_LOGO);
        assertThat(firstSponsor.getUrl()).isEqualTo(SPONSOR_URL);
    }

    @Test
    public void shouldDeleteOldDataBaseWhenUpgrade() throws SQLException {
        final AtomicInteger countCreateDB = new AtomicInteger(0);
        final AtomicInteger countDeleteDB = new AtomicInteger(0);
        //GIVEN a repo with modified real connection to DB
        SponsorRepositoryOrmLite tested =
                new SponsorRepositoryOrmLite(RuntimeEnvironment.application) {
                    @Override
                    void createDB(ConnectionSource connectionSource) throws SQLException {
                        countCreateDB.incrementAndGet();
                    }

                    @Override
                    void deleteOldDatabase(ConnectionSource connectionSource) throws SQLException {
                        countDeleteDB.incrementAndGet();
                    }
                };
        //WHEN testing  upgrade
        tested.onUpgrade(null, null, 0, 1);

        //THEN Repo should delege DB and then create DB again
        assertThat(countDeleteDB.intValue()).isEqualTo(1);
        assertThat(countCreateDB.intValue()).isEqualTo(1);

    }
}
