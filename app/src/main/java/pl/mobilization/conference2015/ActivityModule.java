package pl.mobilization.conference2015;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by msaramak on 29.07.15.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}
