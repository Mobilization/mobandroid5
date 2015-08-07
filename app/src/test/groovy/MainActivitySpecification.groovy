package pl.mobilization.conference2015

import android.support.design.widget.FloatingActionButton
import org.fest.assertions.api.ANDROID
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config;
import pl.polidea.robospock.RoboSpecification

/**
 * Not works because Robolectric 3.0 used.
 * Switch to Robolectric 2.4 or fix Robospock
 */

class MainActivitySpecification /*extends RoboSpecification*/ {

    def "Should display list of sponsors"(){
        setup:
        def mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();

        when:
        FloatingActionButton button =  mainActivity.findViewById(R.id.fab);
        
        then:
        ANDROID.assertThat(button).isNotVisible();
    }

}