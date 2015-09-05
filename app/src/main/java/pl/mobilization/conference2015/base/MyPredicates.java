package pl.mobilization.conference2015.base;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import static com.google.common.base.Predicates.notNull;

/**
 * Created by mario on 05.09.15.
 */
public class MyPredicates {

    public static final Predicate<Object> IS_NOT_EMPTY = new Predicate<Object>() {
        @Override
        public boolean apply(Object input) {
            if (input==null)
            {
                return false;
            }
            return !input.toString().isEmpty();
        }
    };
    public static Predicate<Object> notNullOrEmpty= Predicates.and(notNull(), MyPredicates.IS_NOT_EMPTY);
}