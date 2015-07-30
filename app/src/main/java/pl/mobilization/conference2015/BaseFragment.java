package pl.mobilization.conference2015;


import android.support.v4.app.Fragment;

/**
 * Created by msaramak on 30.07.15.
 */
public class BaseFragment extends Fragment {

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>)getActivity()).getComponent());
    }
}
