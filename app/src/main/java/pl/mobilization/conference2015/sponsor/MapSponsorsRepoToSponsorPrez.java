package pl.mobilization.conference2015.sponsor;

import java.util.List;

import pl.mobilization.conference2015.sponsor.repository.SponsorRepoModel;
import pl.mobilization.conference2015.sponsor.rest.SponsorListRestModel;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestModel;
import pl.mobilization.conference2015.sponsor.view.SponsorViewModel;
import rx.functions.Func1;

/**
 * Created by mario on 13.08.15.
 */
public class MapSponsorsRepoToSponsorPrez implements Func1<List<SponsorRepoModel>, SponsorListRestModel> {


    @Override
    public SponsorListRestModel call(List<SponsorRepoModel> sponsorRepoModels) {
        SponsorListRestModel s = new SponsorListRestModel();
        for (SponsorRepoModel m : sponsorRepoModels) {
            assignSponsorFromDB(s.diamond, m, SponsorViewModel.Level.DIAMOND);
            assignSponsorFromDB(s.platinum, m, SponsorViewModel.Level.PLATINIUM);
            assignSponsorFromDB(s.gold, m, SponsorViewModel.Level.GOLD);
            assignSponsorFromDB(s.silver, m, SponsorViewModel.Level.SILVER);
        }
        return s;
    }

    private void assignSponsorFromDB(List<SponsorRestModel> list, SponsorRepoModel m, SponsorViewModel.Level level) {
        if (m.getLevel() == level.ordinal()) {
            list.add(convert(m));
        }
    }

    private SponsorRestModel convert(SponsorRepoModel m) {
        SponsorRestModel s = new SponsorRestModel();
        s.name = m.getName();
        s.description_html = m.getDescriptionHtml();
        s.link = m.getUrl();
        s.logo_url = m.getLogo();
        return s;
    }

}
