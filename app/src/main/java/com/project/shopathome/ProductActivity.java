package com.project.shopathome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    LottieAnimationView anim;
    TextView tw, price, desc, name;
    int pid, count, total;
    double tp;
    ImageView image;
    private RatingBar Rate;
    private TextView Count;
    ExtendedFloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        pid = getIntent().getIntExtra("id", 0);
        getProductDetails(pid);

        SharedPreferences spInstance = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = spInstance.edit();
        count = spInstance.getInt(String.valueOf(pid), 0);
        total = spInstance.getInt("Total", 0);
        tp = spInstance.getInt("TP", 0);

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        desc = findViewById(R.id.desc);
        image = findViewById(R.id.image);
        tw = findViewById(R.id.tw);
        anim = findViewById(R.id.animationView);
        add = findViewById(R.id.add);
        Rate = findViewById(R.id.rate);
        Count = findViewById(R.id.count);

        tw.setText(String.valueOf(total));

        anim.setOnClickListener(v -> {
            Intent intent = new Intent(ProductActivity.this, HomeActivity.class);
            intent.putExtra("methodName","myMethod");
            startActivity(intent);
            finish();
        });

        add.setOnClickListener(v -> {
            spEditor.putInt(String.valueOf(pid), ++count);
            spEditor.putInt("Total", ++total);
            spEditor.apply();

            HomeActivity.twc.setText(String.valueOf(spInstance.getInt("Total", 0)));

            AdapterCart.Cost= AdapterCart.Cost+Double.parseDouble(String.valueOf(price.getText()));
            HomeActivity.tot.setText("$"+ String.format("%.02f",AdapterCart.Cost));

            anim.playAnimation();
            anim.setMinFrame(25);
            anim.setMaxFrame(95);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    anim.pauseAnimation();
                    tw.animate().translationY(-5).setDuration(300);
                    tw.setText(String.valueOf(total));
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tw.animate().translationY(5).setDuration(300);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    tw.animate().translationY(0).setDuration(300);
                                }
                            }, 200);
                        }
                    }, 200);
                }
            }, 1000);
        });

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.white, ProductActivity.this.getTheme()));
    }

    private void getProductDetails(int pid) {
        Call<List<Results>> call = RetrofitClient.getInstance().getMyApi().getItems();
        call.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                List<Results> model = response.body();
                for (int i = 0; i < model.size(); i++) {
                    if (model.get(i).getID() == pid) {
                        name.setText(model.get(i).getName());
                        price.setText(String.format("%.02f",Double.parseDouble(model.get(i).getPrice())));
                        desc.setText(model.get(i).getDesc());
                        Rate.setRating((float) model.get(i).getRate().rate);
                        Count.setText(String.valueOf(model.get(i).getRate().count));
                        Picasso.get()
                                .load(String.valueOf(model.get(i).getImage()))
                                .into(image);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
}