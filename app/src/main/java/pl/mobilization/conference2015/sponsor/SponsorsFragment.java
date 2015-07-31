package pl.mobilization.conference2015.sponsor;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.common.base.Optional;

import java.security.acl.AclNotFoundException;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import pl.mobilization.conference2015.AndroidApplication;
import pl.mobilization.conference2015.BaseFragment;
import pl.mobilization.conference2015.CheeseListFragment;
import pl.mobilization.conference2015.Cheeses;
import pl.mobilization.conference2015.R;
import pl.mobilization.conference2015.UserComponent;

/**
 * Created by msaramak on 29.07.15.
 */
@Slf4j
public class SponsorsFragment extends BaseFragment implements SponsorsView {

    @Inject
    SponsorPresenter presenter;
    private SponsorRecyclerViewAdapter adapter;


    public SponsorsFragment() {
        log.info("SponsorsFragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        log.debug("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        this.getComponent(UserComponent.class).inject(this);
        presenter.onBindView(getActivity(), this);
        log.debug("onActivityCreated");
        presenter.requestSponsors();

    }

    @Override
    public void onResume() {
        log.debug("resume");
        super.onResume();

    }

    @Override
    public void onPause() {
        log.debug("onpause");
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_sponsors_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    public static class SponsorRecyclerViewAdapter
            extends RecyclerView.Adapter<SponsorRecyclerViewAdapter.ViewHolder> {

        public static final Uri EMPTY_SPONSOR_LOGO = Uri.parse("http://2015.mobilization.pl/images/partners/juglodz.png");
        private SponsorsViewModel model = new SponsorsViewModel();

        public void updateModel(SponsorsViewModel model) {
            this.model = model;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sponsor_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            SponsorViewModel sponsorView = model.getItem(position);
            holder.mTextView.setText(sponsorView.getDisplayName());
            if (sponsorView.isTitle()) {
                holder.mTextView.setTextSize(30.0f);
                return;
            }
            log.debug("logouri " + sponsorView.getLogoUrl());
            Glide.with(holder.mImageView.getContext())
                    .fromUri().load(sponsorView.getLogoUrl().or(EMPTY_SPONSOR_LOGO))
                    .fitCenter()
                    .into(holder.mImageView);
            holder.setModel(sponsorView);

        }

        @Override
        public int getItemCount() {
            return model.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;
            private SponsorViewModel model;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.logo);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
                mImageView.setOnClickListener(this);
                mTextView.setOnClickListener(this);
                mView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (model.getLink().isPresent()){
                    runBrowser();
                }else{
                    showDialogWithDescription(model.getDescription());
                }
            }

            private void showDialogWithDescription(Optional<String> description) {
                log.debug("Show dialog with description " + description.or("Description not avaliable"));
            }

            private void runBrowser() {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, model.getLink().get());
                    mView.getContext().startActivity(browserIntent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(mImageView.getContext(), R.string.browser_not_found, Toast.LENGTH_SHORT).show();
                }
            }

            public void setModel(SponsorViewModel model) {
                this.model = model;
            }
        }
    }


    private void setupRecyclerView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        adapter = new SponsorRecyclerViewAdapter();
        rv.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log.debug("onViewCreated");

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }



    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onUnbindView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onUnbindView();
    }

    @Override
    public void updateSponsors(SponsorsViewModel model) {
        Toast.makeText(getActivity(), "new sponsor model", Toast.LENGTH_SHORT).show();
        adapter.updateModel(model);
    }
}
