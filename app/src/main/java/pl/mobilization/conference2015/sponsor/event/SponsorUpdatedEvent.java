package pl.mobilization.conference2015.sponsor.event;


import lombok.NonNull;
import lombok.Value;
import pl.mobilization.conference2015.sponsor.rest.Sponsors;

/**
 * Created by msaramak on 29.07.15.
 */
@Value
public class SponsorUpdatedEvent {

    private Sponsors sponsors;

    public SponsorUpdatedEvent(@NonNull Sponsors sponsors) {
        this.sponsors = sponsors;
    }
}
