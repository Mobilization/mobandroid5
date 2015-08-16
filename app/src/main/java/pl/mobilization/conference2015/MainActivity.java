/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.mobilization.conference2015;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import lombok.extern.slf4j.Slf4j;
import pl.mobilization.conference2015.sponsor.SponsorsFragment;

@Slf4j
public class MainActivity extends BaseActivity implements HasComponent<UserComponent> {

    private DrawerLayout mDrawerLayout;
    private UserComponent userComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log.info("MainActivity ");

        this.initializeInjector();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mob_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener((view) -> {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                log.info("Snackbar ");
        });
        fab.setVisibility(View.GONE);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void initializeInjector() {
        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(new UserModule())
                .build();

    }

    @Override
    public UserComponent getComponent() {
        return userComponent;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new SponsorsFragment(), "Sponsors");
        adapter.addFragment(new AgendaFragment(), "Agenda");
        adapter.addFragment(new SpeakersFragment(), "Speakers");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_maps:
                                openMap();
                                break;
                            case R.id.nav_add_to_calendar: {
                                Intent intent = generateCalendarEvent();
                                startActivity(intent);
                                break;
                            }
                            case R.id.nav_discussion: {
                                Intent i = getOpenTwitterIntent("mobilizationpl");
                                startActivity(i);
                                break;
                            }
                            case R.id.nav_org: {
                                Intent i = getWWWIntent("http://www.juglodz.pl");
                                startActivity(i);
                                break;
                            }
                            case R.id.nav_author: {
                                startActivity(getGmailIntent());
                                break;
                            }
                        }
                        return true;
                    }
                });
    }

    private Intent getGmailIntent() {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("plain/text");
        sendIntent.setData(Uri.parse("contact@mobilization.pl"));
        sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "contact@mobilization.pl" });
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "mobilization app");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "hello. this is a message sent from mobilization app:");
        startActivity(sendIntent);
        return sendIntent;
    }

    private Intent getWWWIntent(String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }

    private Intent getOpenTwitterIntent(String username) {
        try {
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name="+ username));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + username));
        }
    }

    private void openMap() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:51.7505458,19.4501351?q=51.7505458,19.4501351 (Hala MTŁ aleja Politechniki 4, Łódź)"));
        startActivity(intent);
    }

    @NonNull
    private Intent generateCalendarEvent() {
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 9, 17, 9, 0);
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
        intent.putExtra(CalendarContract.Events.ALL_DAY, false);
        intent.putExtra(CalendarContract.Events.ORGANIZER, "contact@mobilization.pl");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "51.7505458,19.4501351 (Hala MTŁ aleja Politechniki 4, Łódź)");

        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=1");
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis()+9*60*60*1000);
        intent.putExtra(CalendarContract.Events.TITLE, "Mobilization 5");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "http://www.mobilization.pl");
        return intent;
    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
