package pl.mobilization.conference2015.sponsor.rest;

import java.util.List;

import pl.mobilization.conference2015.sponsor.SponsorModel;
import retrofit.RestAdapter;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by msaramak on 29.07.15.
 */
public class SponsorRestServiceRetrofit implements SponsorRestService {

    private SponsorRestService service;

    public SponsorRestServiceRetrofit(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://2015.mobilization.pl/api/")
                .build();
        service = restAdapter.create(SponsorRestService.class);
    }


    @Override
    public Observable<Sponsors> getSponsors() {
        return service.getSponsors();
    }
}
