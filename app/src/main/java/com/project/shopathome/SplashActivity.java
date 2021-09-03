package com.project.shopathome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    ImageView logo,splash;
    LottieAnimationView anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo= findViewById(R.id.logo);
        splash= findViewById(R.id.splash);
        anim= findViewById(R.id.animationView);
        TextView text = findViewById(R.id.quote);
        TextView text1 = findViewById(R.id.quote1);

        logo.animate().translationY(3000).setDuration(1000).setStartDelay(5000);
        anim.animate().translationY(3000).setDuration(1000).setStartDelay(5000);
        text.animate().translationY(3000).setDuration(1000).setStartDelay(5000);
        text1.animate().translationY(3000).setDuration(1000).setStartDelay(5000);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }
}