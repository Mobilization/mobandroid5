package pl.mobilization.conference2015.base;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;

import static com.google.common.base.Predicates.compose;
import static com.google.common.base.Predicates.notNull;

/**
 * Created by mario on 16.08.15.
 */
public class MyPredicates {

    public static final Function<Object, Boolean> IS_NOT_EMPTY = Functions.forPredicate(new Predicate<Object>() {
        @Override
        public boolean apply(Object input) {
            if (input==null)
            {
                return false;
            }
            return !input.toString().isEmpty();
        }
    });
    public static Predicate<Object> notNullOrEmpty= compose(notNull(), MyPredicates.IS_NOT_EMPTY);
}
