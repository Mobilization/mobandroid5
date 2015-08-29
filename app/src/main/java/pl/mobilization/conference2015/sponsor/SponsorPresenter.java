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
import pl.mobilization.conference2015.sponsor.events.OnSponsorClickEvent;
import pl.mobilization.conference2015.sponsor.events.SponsorUpdatedEvent;
import pl.mobilization.conference2015.sponsor.repository.SponsorRepository;
import pl.mobilization.conference2015.sponsor.rest.SponsorListRestModel;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestModel;
import pl.mobilization.conference2015.sponsor.view.SponsorViewModel;
import pl.mobilization.conference2015.sponsor.view.SponsorsListViewModel;
import pl.mobilization.conference2015.sponsor.view.SponsorsView;

import static pl.mobilization.conference2015.sponsor.view.SponsorViewModel.Level.DIAMOND;
import static pl.mobilization.conference2015.sponsor.view.SponsorViewModel.Level.GOLD;
import static pl.mobilization.conference2015.sponsor.view.SponsorViewModel.Level.PLATINIUM;
import static pl.mobilization.conference2015.sponsor.view.SponsorViewModel.Level.SILVER;

/**
 * Created by msaramak on 29.07.15.
 */
@Slf4j
public class SponsorPresenter extends ServicePresenter {
    
    private Context context;

    private SponsorsView view;

    @Inject SponsorRepository sponsorRepository;

    public SponsorPresenter(SponsorRepository repository, EventBus eventBus) {
        super(eventBus);
        sponsorRepository = repository;
    }


    public void onBindView(Context context, SponsorsView view) {
        super.onBindView(context);
        this.view = view;
    }

    public void onEvent(SponsorUpdatedEvent event) {
        sponsorRepository.getSponsors().map(new MapSponsorsRepoToSponsorPrez()).map(sponsors -> {
            SponsorsListViewModel model = convert(sponsors);
            return model;
        }).subscribe(sponsorsViewModel -> {
            view.updateSponsors(sponsorsViewModel);
        });
    }

    public void onEvent(OnSponsorClickEvent event) {
        view.showSponsorDialog(event);
    }

    private SponsorsListViewModel convert(SponsorListRestModel sponsors) {
        SponsorsListViewModel model = new SponsorsListViewModel();
        addSponsorsFromLevel(sponsors.diamond, model, DIAMOND);
        addSponsorsFromLevel(sponsors.platinum, model, PLATINIUM);
        addSponsorsFromLevel(sponsors.gold, model, GOLD);
        addSponsorsFromLevel(sponsors.silver, model, SILVER);
        return model;
    }

    private void addSponsorsFromLevel(List<SponsorRestModel> sponsorRestModels, SponsorsListViewModel model, SponsorViewModel.Level level) {
        if (!sponsorRestModels.isEmpty()) {
            model.addSponsor(SponsorViewModel.title(level));
        }
        for (SponsorRestModel sponsorRestModel : sponsorRestModels) {
            model.addSponsor(SponsorViewModel.convert(sponsorRestModel, level));
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
