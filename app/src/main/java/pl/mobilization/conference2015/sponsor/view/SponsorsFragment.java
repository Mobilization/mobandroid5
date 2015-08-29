package pl.mobilization.conference2015.sponsor.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import lombok.extern.slf4j.Slf4j;
import pl.mobilization.conference2015.BaseFragment;
import pl.mobilization.conference2015.R;
import pl.mobilization.conference2015.UserComponent;
import pl.mobilization.conference2015.sponsor.SponsorPresenter;
import pl.mobilization.conference2015.sponsor.events.OnSponsorClickEvent;
import pl.mobilization.conference2015.ui.component.LogoTitleDialog;

/**
 * Created by msaramak on 29.07.15.
 */
@Slf4j
public class SponsorsFragment extends BaseFragment implements SponsorsView {

    @Inject
    SponsorPresenter presenter;
    private SponsorRecyclerViewAdapter adapter;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(UserComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_sponsors_list, container, false);
        setupRecyclerView(rv);

        presenter.onBindView(getActivity(), this);
        presenter.requestSponsors();
        return rv;
    }

    public static class SponsorRecyclerViewAdapter
            extends RecyclerView.Adapter<SponsorRecyclerViewAdapter.ViewHolder> {

        public static final Uri EMPTY_SPONSOR_LOGO = Uri.parse("http://2015.mobilization.pl/images/partners/juglodz.png");
        private SponsorsListViewModel model = new SponsorsListViewModel();

        public void updateModel(SponsorsListViewModel model) {
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
                holder.mImageView.setVisibility(View.GONE);
            }else{
                holder.mTextView.setTextSize(12.0f);

                holder.mImageView.setVisibility(View.VISIBLE);
                log.debug("logouri " + sponsorView.getLogoUrl());
                Glide.with(holder.mImageView.getContext())
                        .fromUri().load(sponsorView.getLogoUrl().or(EMPTY_SPONSOR_LOGO))
                        .fitCenter()
                        .into(holder.mImageView);
            }
            holder.setModel(sponsorView);

        }

        @Override
        public int getItemCount() {
            return model.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;
            private SponsorViewModel model;
            private EventBus eventBus;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.logo);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
                mView.setOnClickListener(this);
                eventBus = EventBus.getDefault();
            }

            @Override
            public void onClick(View v) {
                if (model.getLink().isPresent()) {
                    runBrowser();
                } else {
                    showDialogWithDescription(model);
                }
            }

            private void showDialogWithDescription(SponsorViewModel model) {
                log.debug("Show dialog with description " + model.description.or("Description not avaliable"));
                eventBus.post(new OnSponsorClickEvent(model));

            }

            private void runBrowser() {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, model.getLink().get());
                    mView.getContext().startActivity(browserIntent);
                } catch (ActivityNotFoundException e) {
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void updateSponsors(SponsorsListViewModel model) {
        adapter.updateModel(model);
    }

    @Override
    public void showSponsorDialog(OnSponsorClickEvent event) {
        LogoTitleDialog dialog = LogoTitleDialog.create(event.getCompanyName(), event.getLogo(), event.getDescriptionHtml());
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        dialog.show(ft, "sponsordialog");
    }
}
