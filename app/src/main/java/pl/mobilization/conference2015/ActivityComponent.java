package pl.mobilization.conference2015;

import android.app.Activity;

import dagger.Component;

/**
 * Created by msaramak on 29.07.15.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    //Exposed to sub-graphs.
    Activity activity();
}