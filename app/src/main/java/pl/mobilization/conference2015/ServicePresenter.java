package pl.mobilization.conference2015;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by msaramak on 31.07.15.
 */
@Slf4j
public abstract class ServicePresenter {


    @Inject
    EventBus eventBus;

    protected Context context;

    protected Messenger mService;

    protected ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            log.info("connected to background service");
            initRequest();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };
    ;

    protected abstract void initRequest();

    public ServicePresenter() {
        eventBus.register(this);
    }

    protected void onBindView(Context context) {
        this.context = context;
        context.bindService(new Intent(context, BackgroundProcessService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    public void onUnbindView() {
        context.unbindService(mConnection);
    }
}
