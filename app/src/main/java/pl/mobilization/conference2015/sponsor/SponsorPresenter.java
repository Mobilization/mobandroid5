package pl.mobilization.conference2015.sponsor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import pl.mobilization.conference2015.BackgroundProcessService;
import pl.mobilization.conference2015.sponsor.event.SponsorUpdatedEvent;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestService;
import pl.mobilization.conference2015.sponsor.storage.SponsorStorage;

/**
 * Created by msaramak on 29.07.15.
 */
public class SponsorPresenter {


    @Inject
    SponsorRestService sponsorService;

    @Inject
    SponsorStorage sponsorStorage;

    @Inject
    EventBus eventBus;

    private Context context;
    private SponsorsView view;

    private Messenger mService;


    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    public void onBindView(Context context, SponsorsView view) {
        this.context = context;
        this.view = view;
        eventBus.register(this);
        ;
        context.bindService(new Intent(context, BackgroundProcessService.class), mConnection,Context.BIND_AUTO_CREATE);

    }

    public void onEvent(SponsorUpdatedEvent event){

    }

    public void onUnbindView(){
        context.unbindService(mConnection);
    }

    public void requestSponsors() {
        Message msg = Message.obtain(null, BackgroundProcessService.UPDATE_SPONSORS, 0, 0);
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
