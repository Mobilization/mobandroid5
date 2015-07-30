package pl.mobilization.conference2015.sponsor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import pl.mobilization.conference2015.AndroidApplication;
import pl.mobilization.conference2015.BaseFragment;
import pl.mobilization.conference2015.UserComponent;

/**
 * Created by msaramak on 29.07.15.
 */
@Slf4j
public class SponsorsFragment extends BaseFragment implements SponsorsView {


    @Inject
    SponsorPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(UserComponent.class).inject(this);
        presenter.onBindView(getActivity(), this);
        log.debug("onActivityCreated");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log.debug("onViewCreated");
        presenter.requestSponsors();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onUnbindView();
    }

    @Override
    public void updateSponsors(SponsorsViewModel model) {

    }
}
