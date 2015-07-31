package pl.mobilization.conference2015;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import javax.inject.Inject;
import javax.inject.Named;

import de.greenrobot.event.EventBus;
import lombok.extern.slf4j.Slf4j;
import pl.mobilization.conference2015.sponsor.event.SponsorUpdatedEvent;
import pl.mobilization.conference2015.sponsor.repository.SponsorRepository;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestService;
import pl.mobilization.conference2015.sponsor.rest.Sponsors;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@Slf4j
public class BackgroundProcessService extends Service {
    public static final int UPDATE_SPONSORS = 2;


    public BackgroundProcessService() {
    }


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

    @Inject
    EventBus eventBus;

    @Inject
    SponsorRestService sponsorRestService;

    @Inject
    SponsorRepository sponsorRepository;

    @Inject @Named("main")
    Scheduler mainScheduler;

    @Inject @Named("internet")
    Scheduler internetSchedyler;

    /**
     *
     */
    final Messenger mMessenger = new Messenger(new IncomingCommandHandler());

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

    private void updateSponsors() {
        log.debug("updateSponsors " + eventBus);
        sponsorRestService.getSponsors().subscribeOn(internetSchedyler)
                .observeOn(mainScheduler).subscribe(new Observer<Sponsors>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Sponsors sponsors) {
                eventBus.post(new SponsorUpdatedEvent(sponsors));
            }
        });


    }
}
