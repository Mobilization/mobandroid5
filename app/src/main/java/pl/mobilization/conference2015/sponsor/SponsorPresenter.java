package pl.mobilization.conference2015.sponsor;

import android.content.Context;
import android.os.Message;
import android.os.RemoteException;

import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import lombok.extern.slf4j.Slf4j;
import pl.mobilization.conference2015.BackgroundProcessService;
import pl.mobilization.conference2015.ServicePresenter;
import pl.mobilization.conference2015.sponsor.event.SponsorUpdatedEvent;
import pl.mobilization.conference2015.sponsor.repository.SponsorRepository;
import pl.mobilization.conference2015.sponsor.rest.Sponsor;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestService;
import pl.mobilization.conference2015.sponsor.rest.Sponsors;

import static pl.mobilization.conference2015.sponsor.SponsorViewModel.Level.DIAMOND;
import static pl.mobilization.conference2015.sponsor.SponsorViewModel.Level.GOLD;
import static pl.mobilization.conference2015.sponsor.SponsorViewModel.Level.PLATINIUM;
import static pl.mobilization.conference2015.sponsor.SponsorViewModel.Level.SILVER;

/**
 * Created by msaramak on 29.07.15.
 */
@Slf4j
public class SponsorPresenter extends ServicePresenter {
    
    private Context context;

    private SponsorsView view;

    public SponsorPresenter(EventBus eventBus) {
        super(eventBus);
    }


    public void onBindView(Context context, SponsorsView view) {
        super.onBindView(context);
        this.view = view;
    }

    public void onEvent(SponsorUpdatedEvent event) {
        Sponsors sponsors = event.getSponsors();
        SponsorsViewModel model = convert(sponsors);
        view.updateSponsors(model);
    }

    public void onEvent(OnSponsorClickEvent event) {
        view.showSponsorDialog(event);
    }

    private SponsorsViewModel convert(Sponsors sponsors) {
        SponsorsViewModel model = new SponsorsViewModel();
        addSponsorsFromLevel(sponsors.diamond, model, DIAMOND);
        addSponsorsFromLevel(sponsors.platinum, model, PLATINIUM);
        addSponsorsFromLevel(sponsors.gold, model, GOLD);
        addSponsorsFromLevel(sponsors.silver, model, SILVER);
        return model;
    }

    private void addSponsorsFromLevel(List<Sponsor> sponsors, SponsorsViewModel model, SponsorViewModel.Level level) {
        if (!sponsors.isEmpty()) {
            model.addSponsor(SponsorViewModel.title(level));
        }
        for (Sponsor sponsor : sponsors) {
            model.addSponsor(SponsorViewModel.convert(sponsor, level));
        }
    }


    public int requestSponsors() {
        Message msg = Message.obtain(null, BackgroundProcessService.UPDATE_SPONSORS, 0, 0);
        try {
            if (mService != null) {
                mService.send(msg);
                return msg.what;
            } else {
                log.debug("Service is null");
            }
        } catch (RemoteException e) {
            throw new RuntimeException("Problem with sending message to service", e);
        }
        return 0;
    }

    @Override
    protected void initRequest() {
        requestSponsors();
    }
}
