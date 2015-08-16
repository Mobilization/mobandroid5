package pl.mobilization.conference2015.base;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import pl.mobilization.conference2015.BuildConfig;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by mario on 16.08.15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MyPredicatesTest  {

    @Test
    public void shouldSetNullValuesAsInvalid(){
        Object val = null;
        boolean valid = MyPredicates.notNullOrEmpty.apply(val);
        assertThat(valid).isFalse();
    }

    @Test
    public void shouldSetEmptyValuesAsInvalid(){
        Object empty = "";
        boolean valid = MyPredicates.notNullOrEmpty.apply(empty);
        assertThat(valid).isFalse();
    }
}