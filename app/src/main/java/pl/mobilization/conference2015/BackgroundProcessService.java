package pl.mobilization.conference2015;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestService;
import pl.mobilization.conference2015.sponsor.storage.SponsorStorage;

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
    SponsorStorage sponsorStorage;

    /**
     *
     */
    final Messenger mMessenger = new Messenger(new IncomingCommandHandler());

    @Override
    public void onCreate() {
        super.onCreate();
        BackgroudComponent component = DaggerBackgroudComponent.create();
        eventBus = component.provideEventBus();
        sponsorRestService = component.provideSponsorRestService();
//        sponsorStorage = component.provideSponsorStorage(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private void updateSponsors() {

    }
}
