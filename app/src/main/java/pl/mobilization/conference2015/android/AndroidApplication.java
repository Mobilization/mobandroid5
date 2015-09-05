package pl.mobilization.conference2015.android;

import android.app.Application;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;

import pl.mobilization.conference2015.BuildConfig;
import pl.mobilization.conference2015.inject.ApplicationComponent;
import pl.mobilization.conference2015.inject.ApplicationModule;

/**
 * Created by msaramak on 29.07.15.
 */
public class AndroidApplication extends Application {
    private ApplicationComponent applicationComponent;

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Crashlytics.start(this);
        initializeInjector();
        configureStrictMode();
    }

    private void configureStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDeathOnNetwork().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().detectActivityLeaks().penaltyLog().build());
        }
    }

    private void initializeInjector() {
//        this.applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();

    }
}
