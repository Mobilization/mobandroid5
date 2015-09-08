package pl.mobilization.conference2015.android.ui.component;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pl.mobilization.conference2015.R;

/**
 * Created by mario on 02.08.15.
 */
public class LogoTitleDialog extends DialogFragment {

    private static final String TITLE_KEY = "TITLE";
    private static final String LOGO_KEY = "LOGO";
    private static final String DESCRIPTION_KEY = "DESCRIPTION";
    String title;
    Uri logo;
    String description;

    ImageView logoImageView;
    TextView titleTv;
    TextView descriptionTv;

    public LogoTitleDialog() {

    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        title = args.getString(TITLE_KEY);
        logo = Uri.parse(args.getString(LOGO_KEY));
        description = args.getString(DESCRIPTION_KEY);
    }

    public static LogoTitleDialog create(String title, Uri logo, String description) {
        LogoTitleDialog dialog = new LogoTitleDialog();
        Bundle b = new Bundle();
        b.putString(TITLE_KEY, title);
        b.putString(LOGO_KEY, logo.toString());
        b.putString(DESCRIPTION_KEY, description);

        dialog.setArguments(b);
        return dialog;
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sponsor_dialog_fragment, container);
        logoImageView = (ImageView)view.findViewById(R.id.logo);
        titleTv = (TextView)view.findViewById(R.id.title);
        descriptionTv = (TextView)view.findViewById(R.id.description);
        titleTv.setText(title);
        descriptionTv.setText(Html.fromHtml(description));
        descriptionTv.setMovementMethod(new ScrollingMovementMethod());
        downloadLogo(inflater);
        return view;
    }

    private void downloadLogo(LayoutInflater inflater) {
        Glide.with(inflater.getContext())
                    .fromUri().load(logo)
                    .fitCenter().into(logoImageView);
    }

}
