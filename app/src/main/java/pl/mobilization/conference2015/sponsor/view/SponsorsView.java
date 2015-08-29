package pl.mobilization.conference2015.sponsor.view;

import pl.mobilization.conference2015.sponsor.events.OnSponsorClickEvent;

/**
 * Created by msaramak on 29.07.15.
 */
public interface SponsorsView {

    public void updateSponsors(SponsorsListViewModel model);

    void showSponsorDialog(OnSponsorClickEvent event);
}
