package pl.mobilization.conference2015;

import dagger.Component;
import pl.mobilization.conference2015.sponsor.view.SponsorsFragment;

/**
 * Created by msaramak on 30.07.15.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {

    void inject(SponsorsFragment userListFragment);

}