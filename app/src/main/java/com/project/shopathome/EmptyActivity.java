package com.project.shopathome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class EmptyActivity extends AppCompatActivity {

    private static final int numP=3;
    private ViewPager viewPager;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.pager);
        Animation anima = AnimationUtils.loadAnimation(this, R.anim.anim);
        viewPager.setAdapter(pagerAdapter);
        viewPager.startAnimation(anima);

        auth = FirebaseAuth.getInstance();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.ob, EmptyActivity.this.getTheme()));

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(EmptyActivity.this, SplashActivity.class));
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
    {

        public ScreenSlidePagerAdapter(@NonNull @NotNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int position) {
            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor=prefs.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();
            switch (position)
            {
                case 0:
                    return new OnBoard1Activity();
                case 1:
                    return new OnBoard2Activity();
                case 2:
                    return new OnBoard3Activity();
            }
            return null;
        }

        @Override
        public int getCount() {
            return numP;
        }
    }
}