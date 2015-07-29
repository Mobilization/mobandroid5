package pl.mobilization.conference2015;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by msaramak on 29.07.15.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {}