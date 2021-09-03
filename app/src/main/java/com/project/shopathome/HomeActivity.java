package com.project.shopathome;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    static LottieAnimationView anim, anim1, anim2;
    private ImageView select, select1,select2;
    static TextView twc, tot;
    static CardView all, men, women, electronics, jewelery;
    private TextView allT, menT, womenT, electronicsT, jeweleryT, sub;
    private ImageView bg;
    private ExtendedFloatingActionButton checkout;
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bg = findViewById(R.id.bg);
        twc = findViewById(R.id.twc);
        anim=findViewById(R.id.animationView1);
        anim1=findViewById(R.id.animationView);
        anim2=findViewById(R.id.animationView2);
        select=findViewById(R.id.select);
        select1=findViewById(R.id.select1);
        select2=findViewById(R.id.select2);
        all=findViewById(R.id.all);
        men=findViewById(R.id.men);
        women=findViewById(R.id.women);
        electronics=findViewById(R.id.electronic);
        jewelery=findViewById(R.id.jewelery);
        allT=findViewById(R.id.text);
        menT=findViewById(R.id.text1);
        womenT=findViewById(R.id.text2);
        electronicsT=findViewById(R.id.text3);
        jeweleryT=findViewById(R.id.text4);
        checkout=findViewById(R.id.checkout);
        sub=findViewById(R.id.sub);
        tot=findViewById(R.id.total);

        checkout.setTranslationX(2000);
        sub.setTranslationX(2000);
        tot.setTranslationX(2000);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.blue, HomeActivity.this.getTheme()));

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String name1 = prefs.getString("Name", null);
        int total = prefs.getInt("Total", 0);
        String[] n =name1.split(" ");
        n[0]="Hello, "+n[0];
        //tw.setText(n[0]);
        twc.setText(String.valueOf(total));

        loadFragment(new FirstFragment());
        anim2.setSpeed(2);

        select.animate().translationY(0).setDuration(1000);
        select1.animate().translationY(-1000).setDuration(1000);
        select2.animate().translationY(-1000).setDuration(1000);

        if(savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
            {
                //Extra bundle is null
            }else{
                String method = extras.getString("methodName");

                if (method.equals("myMethod"))
                {
                    select.animate().translationY(-1000).setDuration(1000);
                    select1.animate().translationY(0).setDuration(1000);
                    select2.animate().translationY(-1000).setDuration(1000);

                    resetChoice();
                    anim1.playAnimation();
                    anim1.setMinFrame(20);
                    anim1.setMaxFrame(28);

                    checkout.animate().translationX(0).setDuration(1000);
                    sub.animate().translationX(0).setDuration(1000);
                    tot.animate().translationX(0).setDuration(1000);
                    all.animate().translationX(-2000).setDuration(1000);
                    men.animate().translationX(-2000).setDuration(1000);
                    women.animate().translationX(-2000).setDuration(1000);
                    electronics.animate().translationX(-2000).setDuration(1000);
                    jewelery.animate().translationX(-2000).setDuration(1000);
                    allT.animate().translationX(-2000).setDuration(1000);
                    menT.animate().translationX(-2000).setDuration(1000);
                    womenT.animate().translationX(-2000).setDuration(1000);
                    electronicsT.animate().translationX(-2000).setDuration(1000);
                    jeweleryT.animate().translationX(-2000).setDuration(1000);
                    bg.animate().translationY(0).setDuration(1000);

                    loadFragment(new SecondFragment());

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            anim1.pauseAnimation();
                        }
                    }, 500);
                }
            }
        }

        all.setOnClickListener(v -> {
            resetChoice();
            all.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green));
            loadFragment(new ViewFragment(0));
        });

        men.setOnClickListener(v -> {
            resetChoice();
            men.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green));
            loadFragment(new ViewFragment(1));
        });

        women.setOnClickListener(v -> {
            resetChoice();
            women.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green));
            loadFragment(new ViewFragment(2));
        });

        electronics.setOnClickListener(v -> {
            resetChoice();
            electronics.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green));
            loadFragment(new ViewFragment(3));
        });

        jewelery.setOnClickListener(v -> {
            resetChoice();
            jewelery.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green));
            loadFragment(new ViewFragment(4));
        });

        checkout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });

        anim.setOnClickListener(v -> {
            resetChoice();
            anim.playAnimation();

            select.animate().translationY(0).setDuration(1000);
            select1.animate().translationY(-1000).setDuration(1000);
            select2.animate().translationY(-1000).setDuration(1000);

            all.animate().translationX(0).setDuration(1000);
            men.animate().translationX(0).setDuration(1000);
            women.animate().translationX(0).setDuration(1000);
            electronics.animate().translationX(0).setDuration(1000);
            jewelery.animate().translationX(0).setDuration(1000);
            allT.animate().translationX(0).setDuration(1000);
            menT.animate().translationX(0).setDuration(1000);
            womenT.animate().translationX(0).setDuration(1000);
            electronicsT.animate().translationX(0).setDuration(1000);
            jeweleryT.animate().translationX(0).setDuration(1000);
            checkout.animate().translationX(2000).setDuration(1000);
            sub.animate().translationX(2000).setDuration(1000);
            tot.animate().translationX(2000).setDuration(1000);
            bg.animate().translationY(0).setDuration(1000);


            loadFragment(new FirstFragment());

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    anim.pauseAnimation();
                    anim.setFrame(0);
                }
            }, 1500);
        });

        anim1.setOnClickListener(v -> {
            resetChoice();
            anim1.playAnimation();
            anim1.setMinFrame(20);
            anim1.setMaxFrame(28);

            checkout.animate().translationX(0).setDuration(1000);
            sub.animate().translationX(0).setDuration(1000);
            tot.animate().translationX(0).setDuration(1000);
            all.animate().translationX(-2000).setDuration(1000);
            men.animate().translationX(-2000).setDuration(1000);
            women.animate().translationX(-2000).setDuration(1000);
            electronics.animate().translationX(-2000).setDuration(1000);
            jewelery.animate().translationX(-2000).setDuration(1000);
            allT.animate().translationX(-2000).setDuration(1000);
            menT.animate().translationX(-2000).setDuration(1000);
            womenT.animate().translationX(-2000).setDuration(1000);
            electronicsT.animate().translationX(-2000).setDuration(1000);
            jeweleryT.animate().translationX(-2000).setDuration(1000);
            bg.animate().translationY(0).setDuration(1000);

            select.animate().translationY(-1000).setDuration(1000);
            select1.animate().translationY(0).setDuration(1000);
            select2.animate().translationY(-1000).setDuration(1000);

            loadFragment(new SecondFragment());

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    anim1.pauseAnimation();
                }
            }, 500);
        });

        anim2.setOnClickListener(v -> {
            resetChoice();
            anim2.playAnimation();

            //loadFragment(new ThirdFragment());
            checkout.animate().translationX(2000).setDuration(1000);
            sub.animate().translationX(2000).setDuration(1000);
            tot.animate().translationX(2000).setDuration(1000);
            all.animate().translationX(-2000).setDuration(1000);
            men.animate().translationX(-2000).setDuration(1000);
            women.animate().translationX(-2000).setDuration(1000);
            electronics.animate().translationX(-2000).setDuration(1000);
            jewelery.animate().translationX(-2000).setDuration(1000);
            allT.animate().translationX(-2000).setDuration(1000);
            menT.animate().translationX(-2000).setDuration(1000);
            womenT.animate().translationX(-2000).setDuration(1000);
            electronicsT.animate().translationX(-2000).setDuration(1000);
            jeweleryT.animate().translationX(-2000).setDuration(1000);
            select.animate().translationY(-1000).setDuration(1000);
            select1.animate().translationY(-1000).setDuration(1000);
            select2.animate().translationY(0).setDuration(1000);


            bg.animate().translationY(-1000).setDuration(1000);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    anim2.pauseAnimation();
                }
            }, 1500);
        });
    }

    boolean PressedOnce=false;
    @Override
    public void onBackPressed() {
        Fragment f = this.getFragmentManager().findFragmentById(R.id.frameLayout);
        if(f instanceof FirstFragment) {
            if (this.PressedOnce) {
                finishAffinity();
                System.exit(0);
            }
            Toast.makeText(HomeActivity.this, "Press Back Again To Exit", Toast.LENGTH_SHORT).show();
            this.PressedOnce = true;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                @Override
                public void run() {
                    PressedOnce=false;
                }
            }, 2000);
        }
        else {
            resetChoice();
            anim.playAnimation();

            select.animate().translationY(0).setDuration(1000);
            select1.animate().translationY(-1000).setDuration(1000);
            select2.animate().translationY(-1000).setDuration(1000);

            all.animate().translationX(0).setDuration(1000);
            men.animate().translationX(0).setDuration(1000);
            women.animate().translationX(0).setDuration(1000);
            electronics.animate().translationX(0).setDuration(1000);
            jewelery.animate().translationX(0).setDuration(1000);
            allT.animate().translationX(0).setDuration(1000);
            menT.animate().translationX(0).setDuration(1000);
            womenT.animate().translationX(0).setDuration(1000);
            electronicsT.animate().translationX(0).setDuration(1000);
            jeweleryT.animate().translationX(0).setDuration(1000);
            checkout.animate().translationX(2000).setDuration(1000);
            sub.animate().translationX(2000).setDuration(1000);
            tot.animate().translationX(2000).setDuration(1000);

            loadFragment(new FirstFragment());

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    anim.pauseAnimation();
                    anim.setFrame(0);
                }
            }, 1500);
        }
    }

    private void resetChoice(){
        all.setCardBackgroundColor(ContextCompat.getColor(this, R.color.main));
        men.setCardBackgroundColor(ContextCompat.getColor(this, R.color.main));
        women.setCardBackgroundColor(ContextCompat.getColor(this, R.color.main));
        electronics.setCardBackgroundColor(ContextCompat.getColor(this, R.color.main));
        jewelery.setCardBackgroundColor(ContextCompat.getColor(this, R.color.main));
    }

    private void loadFragment(Fragment fragment) {
        fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}