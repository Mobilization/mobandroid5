package pl.mobilization.conference2015.sponsor.events;

import android.net.Uri;

import pl.mobilization.conference2015.sponsor.view.SponsorViewModel;

/**
 * Created by mario on 02.08.15.
 */
public class OnSponsorClickEvent {
    private SponsorViewModel model;

    public OnSponsorClickEvent(SponsorViewModel model) {
        this.model = model;
    }

    public String getCompanyName() {
        return model.displayName;
    }

    public String getDescriptionHtml() {
        return model.description.or("No more info");
    }

    public Uri getLogo() {
        return model.logoUrl.get();
    }

}
