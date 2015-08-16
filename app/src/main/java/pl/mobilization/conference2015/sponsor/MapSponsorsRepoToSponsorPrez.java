package pl.mobilization.conference2015.sponsor;

import java.util.List;

import pl.mobilization.conference2015.BackgroundProcessService;
import pl.mobilization.conference2015.sponsor.SponsorRepoModel;
import pl.mobilization.conference2015.sponsor.SponsorViewModel;
import pl.mobilization.conference2015.sponsor.rest.Sponsor;
import pl.mobilization.conference2015.sponsor.rest.Sponsors;
import rx.functions.Func1;

/**
 * Created by mario on 13.08.15.
 */
public class MapSponsorsRepoToSponsorPrez implements Func1<List<SponsorRepoModel>, Sponsors> {


    @Override
    public Sponsors call(List<SponsorRepoModel> sponsorRepoModels) {
        Sponsors s = new Sponsors();
        for (SponsorRepoModel m : sponsorRepoModels) {
            assignSponsorFromDB(s.diamond, m, SponsorViewModel.Level.DIAMOND);
            assignSponsorFromDB(s.platinum, m, SponsorViewModel.Level.PLATINIUM);
            assignSponsorFromDB(s.gold, m, SponsorViewModel.Level.GOLD);
            assignSponsorFromDB(s.silver, m, SponsorViewModel.Level.SILVER);
        }
        return s;
    }

    private void assignSponsorFromDB(List<Sponsor> list, SponsorRepoModel m, SponsorViewModel.Level level) {
        if (m.getLevel() == level.ordinal()) {
            list.add(convert(m));
        }
    }

    private Sponsor convert(SponsorRepoModel m) {
        Sponsor s = new Sponsor();
        s.name = m.getName();
        s.description_html = m.getDescriptionHtml();
        s.link = m.getUrl();
        s.logo_url = m.getLogo();
        return s;
    }

}
