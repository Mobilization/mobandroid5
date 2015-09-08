package pl.mobilization.conference2015.android.ui.component;

import android.net.Uri;
import android.view.LayoutInflater;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import pl.mobilization.conference2015.BuildConfig;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by msaramak on 07.08.15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LogoTitleDialogTest {

    @Test
    public void shouldDisplayLogoDialogWithCorrectParams(){
        //GIVEN a 3 not null params, title, description
        String title = "title";
        Uri logoUri = Uri.parse("http://2015.mobilization.pl/images/sponsors/seamless.png");
        String description = "Seamless Description";
        //WHEN creating dialog
        LogoTitleDialog dialog = LogoTitleDialog.create(title, logoUri, description);
        //THEN correct params go to correct fields
        assertThat(dialog.title).isEqualTo(title);
        assertThat(dialog.description).isEqualTo(description);
        assertThat(dialog.logo).isEqualTo(logoUri);
    }


    @Test
    public void shouldDisplayLogoDialog(){
        //GIVEN a 3 not null params, title, description
        String title = "title";
        Uri logoUri = Uri.parse("http://2015.mobilization.pl/images/sponsors/seamless.png");
        String description = "Seamless Description";

        //WHEN creating dialog
        LogoTitleDialog dialog = LogoTitleDialog.create(title, logoUri, description);

        dialog.onCreateView(LayoutInflater.from(RuntimeEnvironment.application), null, null);
        //THEN correct params go to correct fields
        assertThat(dialog.titleTv.getText()).isEqualTo(title);
        assertThat(dialog.descriptionTv.getText().toString()).isEqualTo(description);
        assertThat(dialog.logoImageView.getWidth()).isEqualTo(0);
    }

}
