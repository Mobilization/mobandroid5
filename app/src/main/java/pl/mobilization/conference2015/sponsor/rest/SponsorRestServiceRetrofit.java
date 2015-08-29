package pl.mobilization.conference2015.sponsor.rest;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by msaramak on 29.07.15.
 */
public class SponsorRestServiceRetrofit implements SponsorRestService {

    public static final String CONFERENCE_MAIN_URL = "http://2015.mobilization.pl";
    public static final String API = "/api/";
    private SponsorRestService service;

    public SponsorRestServiceRetrofit() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CONFERENCE_MAIN_URL + API)
                .build();
        service = restAdapter.create(SponsorRestService.class);
    }


    @Override
    public Observable<SponsorListRestModel> getSponsors() {
        return service.getSponsors();
    }
}
