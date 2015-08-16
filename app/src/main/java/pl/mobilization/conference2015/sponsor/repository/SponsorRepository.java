package pl.mobilization.conference2015.sponsor.repository;


import java.util.List;

import pl.mobilization.conference2015.sponsor.SponsorRepoModel;
import pl.mobilization.conference2015.sponsor.rest.Sponsors;
import rx.Observable;

/**
 * Created by msaramak on 29.07.15.
 */
public interface SponsorRepository {

    Observable<List<SponsorRepoModel>> getSponsors();

    void saveSponsors(List<SponsorRepoModel> models);
}
