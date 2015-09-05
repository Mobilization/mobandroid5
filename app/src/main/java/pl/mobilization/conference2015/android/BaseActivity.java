package pl.mobilization.conference2015.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by msaramak on 29.07.15.
 */
public class BaseActivity extends AppCompatActivity {

    @Inject
    EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
