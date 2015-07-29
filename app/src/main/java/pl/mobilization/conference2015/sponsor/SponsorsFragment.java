package pl.mobilization.conference2015.sponsor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by msaramak on 29.07.15.
 */
public class SponsorsFragment extends Fragment implements SponsorsView{


    private SponsorPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SponsorComponent sponsorComponent = DaggerSponsorComponent.builder().sponsorModule(new SponsorModule()).build();
        presenter = sponsorComponent.provideSponsorPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        presenter.onBindView(getActivity(), this);
        presenter.requestSponsors();
        return view;
    }

    @Override
    public void updateSponsors(SponsorsViewModel model) {

    }
}
