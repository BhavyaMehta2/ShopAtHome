package com.project.shopathome;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    static LottieAnimationView anim, anim1, anim2;
    private ImageView select, select1, select2;
    static TextView twc, tot;
    static CardView all, men, women, electronics, jewelery;
    private TextView allT, menT, womenT, electronicsT, jeweleryT, sub;
    private ImageView bg;
    private TextInputLayout search;
    private ImageView search1;
    static ExtendedFloatingActionButton checkout, logout;
    static TextView email;
    FragmentManager fm;

    static ImageView tint;
    static CardView confirm;
    static TextInputLayout check;
    static TextView close, delconf;
    static TextInputLayout pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bg = findViewById(R.id.bg);
        twc = findViewById(R.id.twc);
        anim = findViewById(R.id.animationView1);
        anim1 = findViewById(R.id.animationView);
        anim2 = findViewById(R.id.animationView2);
        select = findViewById(R.id.select);
        select1 = findViewById(R.id.select1);
        select2 = findViewById(R.id.select2);
        all = findViewById(R.id.all);
        men = findViewById(R.id.men);
        women = findViewById(R.id.women);
        electronics = findViewById(R.id.electronic);
        jewelery = findViewById(R.id.jewelery);
        allT = findViewById(R.id.text);
        menT = findViewById(R.id.text1);
        womenT = findViewById(R.id.text2);
        electronicsT = findViewById(R.id.text3);
        jeweleryT = findViewById(R.id.text4);
        checkout = findViewById(R.id.checkout);
        sub = findViewById(R.id.sub);
        tot = findViewById(R.id.total);
        search = findViewById(R.id.search);
        search1 = findViewById(R.id.search1);
        logout = findViewById(R.id.logout);

        tint = findViewById(R.id.tint);
        confirm = findViewById(R.id.card);
        close = findViewById(R.id.close);
        check = findViewById(R.id.fcheck);
        delconf = findViewById(R.id.delconf);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);

        checkout.setTranslationY(-2000);
        logout.setTranslationX(2000);
        sub.setTranslationY(-2000);
        tot.setTranslationY(-2000);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.blue, HomeActivity.this.getTheme()));

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor spEditor = prefs.edit();
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor Editor = pref.edit();
        int total = prefs.getInt("Total", 0);
        twc.setText(String.valueOf(total));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        email.setText(user.getEmail());

        close.setOnClickListener(view -> {
            tint.setVisibility(View.GONE);
            confirm.setVisibility(View.GONE);
            window.setStatusBarColor(getResources().getColor(R.color.blue, HomeActivity.this.getTheme()));
        });

        tint.setOnClickListener(view -> {
            tint.setVisibility(View.GONE);
            confirm.setVisibility(View.GONE);
            window.setStatusBarColor(getResources().getColor(R.color.blue, HomeActivity.this.getTheme()));
        });

        if(tint.getVisibility()==View.VISIBLE)
            window.setStatusBarColor(getResources().getColor(R.color.blueb, HomeActivity.this.getTheme()));

        delconf.setOnClickListener(view -> {
            String pass1=pass.getEditText().getText().toString();
            if (TextUtils.isEmpty(pass1)) {
                pass.setError("Field cannot be empty");
            }
            else {
                pass.setError("");
                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), pass.getEditText().getText().toString());
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (check.getEditText().getText().toString().equals("Delete My Account")) {
                                    assert user != null;
                                    user.delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(HomeActivity.this, "User account deleted.", Toast.LENGTH_LONG).show();
                                                        spEditor.clear();
                                                        Editor.clear();
                                                        spEditor.apply();
                                                        Editor.apply();
                                                        auth.signOut();
                                                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                } else
                                    Toast.makeText(HomeActivity.this, "User account not deleted.", Toast.LENGTH_LONG).show();
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(check.getEditText().getWindowToken(), 0);
                            }
                        });
            }
        });

        loadFragment(new FirstFragment());
        anim2.setSpeed(2);

        select.animate().translationY(0).setDuration(1000);
        select1.animate().translationY(-1000).setDuration(1000);
        select2.animate().translationY(-1000).setDuration(1000);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                String method = extras.getString("methodName");

                if (method.equals("myMethod")) {
                    select.animate().translationY(-1000).setDuration(1000);
                    select1.animate().translationY(0).setDuration(1000);
                    select2.animate().translationY(-1000).setDuration(1000);

                    resetChoice();
                    anim1.playAnimation();
                    anim1.setMinFrame(20);
                    anim1.setMaxFrame(28);

                    checkout.animate().translationY(0).setDuration(1000);
                    sub.animate().translationY(0).setDuration(1000);
                    tot.animate().translationY(0).setDuration(1000);
                    logout.animate().translationX(2000).setDuration(1000);
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

        search.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    Log.e("TAG", "Done pressed");
                    String query = search.getEditText().getText().toString();
                    if (!query.isEmpty()) {
                        resetChoice();
                        String queries[] = query.toUpperCase().split(" ");
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
                        checkout.animate().translationY(-2000).setDuration(1000);
                        sub.animate().translationY(-2000).setDuration(1000);
                        tot.animate().translationY(-2000).setDuration(1000);
                        logout.animate().translationX(2000).setDuration(1000);

                        loadFragment(new FirstFragment());
                        loadFragment(new ViewFragment(5, queries));

                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(search.getEditText().getWindowToken(), 0);
                        return true;
                    }
                }
                return false;
            }
        });

        logout.setOnClickListener(v ->

        {
            spEditor.clear();
            Editor.clear();
            spEditor.apply();
            Editor.apply();
            auth.signOut();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finishAffinity();
            HomeActivity.this.startActivity(intent);
        });

        search1.setOnClickListener(v ->

        {
            String query = search.getEditText().getText().toString();
            if (!query.isEmpty()) {
                resetChoice();
                String queries[] = query.toUpperCase().split(" ");
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
                checkout.animate().translationY(-2000).setDuration(1000);
                sub.animate().translationY(-2000).setDuration(1000);
                tot.animate().translationY(-2000).setDuration(1000);
                logout.animate().translationX(2000).setDuration(1000);

                loadFragment(new ViewFragment(5, queries));

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search.getEditText().getWindowToken(), 0);
            }
        });

        all.setOnClickListener(v ->

        {
            resetChoice();
            all.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green));
            loadFragment(new ViewFragment(0, new String[1]));
        });

        men.setOnClickListener(v ->

        {
            resetChoice();
            men.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green));
            loadFragment(new ViewFragment(1, new String[1]));
        });

        women.setOnClickListener(v ->

        {
            resetChoice();
            women.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green));
            loadFragment(new ViewFragment(2, new String[1]));
        });

        electronics.setOnClickListener(v ->

        {
            resetChoice();
            electronics.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green));
            loadFragment(new ViewFragment(3, new String[1]));
        });

        jewelery.setOnClickListener(v ->

        {
            resetChoice();
            jewelery.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green));
            loadFragment(new ViewFragment(4, new String[1]));
        });

        checkout.setOnClickListener(v ->

        {
            if (SecondFragment.List.size() == 0) {
                Toast.makeText(HomeActivity.this, "Your cart is empty ＞﹏＜", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(HomeActivity.this, CheckoutActivity.class);
                spEditor.putString("Cart", String.valueOf(tot.getText()));
                spEditor.apply();
                startActivity(intent);
            }
        });

        anim.setOnClickListener(v ->

        {
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
            checkout.animate().translationY(-2000).setDuration(1000);
            sub.animate().translationY(-2000).setDuration(1000);
            tot.animate().translationY(-2000).setDuration(1000);
            logout.animate().translationX(2000).setDuration(1000);

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

        anim1.setOnClickListener(v ->

        {
            resetChoice();
            anim1.playAnimation();
            anim1.setMinFrame(20);
            anim1.setMaxFrame(28);

            checkout.animate().translationY(0).setDuration(1000);
            sub.animate().translationY(0).setDuration(1000);
            tot.animate().translationY(0).setDuration(1000);
            logout.animate().translationX(2000).setDuration(1000);
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

        anim2.setOnClickListener(v ->

        {
            resetChoice();
            anim2.playAnimation();
            loadFragment(new ThirdFragment());

            checkout.animate().translationY(-2000).setDuration(1000);
            sub.animate().translationY(-2000).setDuration(1000);
            tot.animate().translationY(-2000).setDuration(1000);
            logout.animate().translationX(0).setDuration(1000);
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

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    anim2.pauseAnimation();
                }
            }, 1500);
        });
    }

    boolean PressedOnce = false;

    @Override
    public void onBackPressed() {
        Fragment f = this.getFragmentManager().findFragmentById(R.id.frameLayout);
        if(confirm.getVisibility()==View.VISIBLE)
        {
            confirm.setVisibility(View.GONE);
            tint.setVisibility(View.GONE);
        }
        else if (f instanceof FirstFragment) {
            if (this.PressedOnce) {
                finishAffinity();
                System.exit(0);
            }
            Toast.makeText(HomeActivity.this, "Press Back Again To Exit", Toast.LENGTH_SHORT).show();
            this.PressedOnce = true;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                @Override
                public void run() {
                    PressedOnce = false;
                }
            }, 2000);
        } else {
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
            checkout.animate().translationY(-2000).setDuration(1000);
            sub.animate().translationY(-2000).setDuration(1000);
            tot.animate().translationY(-2000).setDuration(1000);
            logout.animate().translationX(2000).setDuration(1000);

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

    private void resetChoice() {
        all.setCardBackgroundColor(ContextCompat.getColor(this, R.color.main));
        men.setCardBackgroundColor(ContextCompat.getColor(this, R.color.main));
        women.setCardBackgroundColor(ContextCompat.getColor(this, R.color.main));
        electronics.setCardBackgroundColor(ContextCompat.getColor(this, R.color.main));
        jewelery.setCardBackgroundColor(ContextCompat.getColor(this, R.color.main));
        getWindow().getDecorView().clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search.getEditText().getWindowToken(), 0);
    }

    private void loadFragment(Fragment fragment) {
        fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void delAcc() {

    }
}