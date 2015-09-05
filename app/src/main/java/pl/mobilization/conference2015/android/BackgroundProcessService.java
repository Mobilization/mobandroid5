package pl.mobilization.conference2015.android;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import de.greenrobot.event.EventBus;
import lombok.extern.slf4j.Slf4j;
import pl.mobilization.conference2015.android.AndroidApplication;
import pl.mobilization.conference2015.sponsor.MapSponsorsRepoToSponsorPrez;
import pl.mobilization.conference2015.sponsor.events.SponsorUpdatedEvent;
import pl.mobilization.conference2015.sponsor.repository.SponsorRepoModel;
import pl.mobilization.conference2015.sponsor.repository.SponsorRepository;
import pl.mobilization.conference2015.sponsor.rest.SponsorListRestModel;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestModel;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestService;
import pl.mobilization.conference2015.sponsor.view.SponsorViewModel;
import rx.Observer;
import rx.Scheduler;
import rx.functions.Action1;

import static pl.mobilization.conference2015.sponsor.view.SponsorViewModel.Level.DIAMOND;
import static pl.mobilization.conference2015.sponsor.view.SponsorViewModel.Level.GOLD;
import static pl.mobilization.conference2015.sponsor.view.SponsorViewModel.Level.PLATINIUM;
import static pl.mobilization.conference2015.sponsor.view.SponsorViewModel.Level.SILVER;

@Slf4j
public class BackgroundProcessService extends Service {
    public static final int UPDATE_SPONSORS = 2;
    /**
     *
     */
    final Messenger mMessenger = new Messenger(new IncomingCommandHandler());
    @Inject
    EventBus eventBus;
    @Inject
    SponsorRestService sponsorRestService;
    @Inject
    SponsorRepository sponsorRepository;
    @Inject
    @Named("main")
    Scheduler mainScheduler;
    @Inject
    @Named("internet")
    Scheduler internetSchedyler;

    public BackgroundProcessService() {
    }


    /**
     * communication between UI and Background service
     */
    class IncomingCommandHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_SPONSORS:
                    updateSponsors();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }


    }

    @Override
    public void onCreate() {
        super.onCreate();
        log.info("BackgroundProcessService creating");
        ((AndroidApplication) getApplicationContext()).getApplicationComponent().inject(this);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    /**
     * Important
     */
    private void updateSponsors() {
        log.debug("updateSponsors " + eventBus);
        takeSponsorListFromRepoAndSendToUI();
        takeSponsorsFromRestSaveAndSendToUI();


    }

    private void takeSponsorsFromRestSaveAndSendToUI() {
        sponsorRestService.getSponsors().doOnNext(new SaveSponsorsFromRest(sponsorRepository))
                .subscribeOn(internetSchedyler)
                .observeOn(mainScheduler)
                .subscribe(new UpdateSponsorsEventGenerator(getApplicationContext(), eventBus));
    }

    private void takeSponsorListFromRepoAndSendToUI() {


        sponsorRepository.getSponsors()
                .subscribeOn(internetSchedyler)
                .observeOn(mainScheduler).map(new MapSponsorsRepoToSponsorPrez()).subscribe(new UpdateSponsorsEventGenerator(getApplicationContext(), eventBus));
    }


    private static class SaveSponsorsFromRest implements Action1<SponsorListRestModel> {


        private SponsorRepository sponsorRepository;

        public SaveSponsorsFromRest(SponsorRepository sponsorRepository) {
            this.sponsorRepository = sponsorRepository;
        }

        private void addSponsorsToRepo(List<SponsorRestModel> list, List<SponsorRepoModel> models, SponsorViewModel.Level level) {
            for (SponsorRestModel s : list) {
                SponsorRepoModel model = convert(s, level);
                models.add(model);
            }
        }

        @NonNull
        private SponsorRepoModel convert(SponsorRestModel s, SponsorViewModel.Level level) {
            SponsorRepoModel model = SponsorRepoModel.
                    builder(s.name).level(level.ordinal()).
                    description(s.description_html).
                    logo(s.logo_url).url(s.link).build();
            return model;
        }

        @Override
        public void call(SponsorListRestModel sponsors) {
            List<SponsorRepoModel> models = new ArrayList<SponsorRepoModel>();
            addSponsorsToRepo(sponsors.diamond, models, DIAMOND);
            addSponsorsToRepo(sponsors.platinum, models, PLATINIUM);
            addSponsorsToRepo(sponsors.gold, models, GOLD);
            addSponsorsToRepo(sponsors.silver, models, SILVER);
            sponsorRepository.saveSponsors(models);
        }
    }


    private static class UpdateSponsorsEventGenerator implements Observer<SponsorListRestModel> {
        private Context context;
        private EventBus eventBus;

        public UpdateSponsorsEventGenerator(Context context, EventBus eventBus) {
            this.context = context;
            this.eventBus = eventBus;
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(SponsorListRestModel sponsors) {
            eventBus.post(new SponsorUpdatedEvent());
        }
    }

}
