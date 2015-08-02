package pl.mobilization.conference2015.sponsor;

/**
 * Created by msaramak on 29.07.15.
 */
public interface SponsorsView {

    public void updateSponsors(SponsorsViewModel model);

    void showSponsorDialog(OnSponsorClickEvent event);
}
