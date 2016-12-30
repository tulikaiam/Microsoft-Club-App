package com.example.rishabh.microsoftclubapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gc.materialdesign.views.ButtonFlat;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class SecondActivity extends FragmentActivity {
    private ViewPager pager;
    private SmartTabLayout indicator;
    private ButtonFlat skip;
    private ButtonFlat next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        pager = (ViewPager)findViewById(R.id.pager);
        indicator = (SmartTabLayout)findViewById(R.id.indicator);
        skip = (ButtonFlat)findViewById(R.id.skip);
        next = (ButtonFlat)findViewById(R.id.next);
        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0 : return new ScreenFragment1();
                    case 1 : return new ScreenFragment2();
                    case 2 : return new ScreenFragment3();
                    default: return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishOnboarding();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pager.getCurrentItem() == 2) { // The last screen
                    finishOnboarding();
                } else {
                    pager.setCurrentItem(
                            pager.getCurrentItem() + 1,
                            true
                    );
                }
            }
        });
        indicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                    skip.setVisibility(View.GONE);
                    next.setText("Done");
                } else {
                    skip.setVisibility(View.VISIBLE);
                    next.setText("Next");
                }
            }
        });
    }
    private void finishOnboarding() {
        // Get the shared preferences
        SharedPreferences preferences =
                getSharedPreferences("my_preferences", MODE_PRIVATE);

        // Set onboarding_complete to true
        preferences.edit()
                .putBoolean("onboarding_complete",true).apply();

        // Launch the main Activity, called MainActivity
        Intent main = new Intent(this, ThirdActivity.class);
        startActivity(main);

        // Close the OnboardingActivity
        finish();
    }

}

