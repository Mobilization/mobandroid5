package pl.mobilization.conference2015.sponsor.rest;

import java.util.List;

import pl.mobilization.conference2015.sponsor.SponsorModel;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by msaramak on 29.07.15.
 */
public interface SponsorRestService {

    @GET("/sponsors.json")
    public Observable<Sponsors> getSponsors();
}
