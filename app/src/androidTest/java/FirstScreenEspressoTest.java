import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import pl.mobilization.conference2015.MainActivity;
import pl.mobilization.conference2015.R;
import test.android.utils.DisableAnimationsRule;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.closeDrawer;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by msaramak on 07.08.15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FirstScreenEspressoTest  {

    @ClassRule
    public static DisableAnimationsRule disableAnimationsRule = new DisableAnimationsRule();

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void shouldShowFirstScreen() {
        onView(withText("Mobilization 5")).check(matches(isDisplayed()));
    }

    @Test
    public void shouldOpenAndCloseDr() {
        openDrawer(R.id.mob_drawer_layout);
        onView(withText("Home")).perform(click());
        closeDrawer(R.id.mob_drawer_layout);
        //pressBack();
    }



}