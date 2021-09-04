package com.project.shopathome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {

    private TextView cost;
    private ExtendedFloatingActionButton place;
    static SharedPreferences spInstance;
    static SharedPreferences.Editor spEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        cost = findViewById(R.id.cost);
        place = findViewById(R.id.order);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new PayFragment());
        fragmentTransaction.commit();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.blue, PayActivity.this.getTheme()));

        spInstance = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        spEditor = spInstance.edit();
        cost.setText(spInstance.getString("Cart",""));

        place.setOnClickListener(v -> {
            Toast.makeText(PayActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();

            spEditor.clear();
            spEditor.apply();

            Intent intent = new Intent(PayActivity.this, HomeActivity.class);
            finish();
            startActivity(intent);
        });
    }
}