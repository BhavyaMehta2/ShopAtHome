package com.project.shopathome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class NameAddActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView top;
    private ExtendedFloatingActionButton con;
    private TextInputLayout name, add, land, pin, city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_add);

        spinner = findViewById(R.id.spinner1);
        con= findViewById(R.id.con);
        name = findViewById(R.id.editText1);
        add = findViewById(R.id.editText2);
        land = findViewById(R.id.editText3);
        pin = findViewById(R.id.editText4);
        city = findViewById(R.id.editText5);

        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        String name1 = prefs.getString("Name", null);

        if(name1!=null&&!name1.isEmpty())
            {
                Intent intent = new Intent(NameAddActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

        con.setOnClickListener(v -> {
            String names = name.getEditText().getText().toString();
            if(names.isEmpty())
                name.setError("Field cannot be empty");
            else
                name.setError(null);
            String address = add.getEditText().getText().toString();
            if(address.isEmpty())
                add.setError("Field cannot be empty");
            else
                add.setError(null);
            String landmark = land.getEditText().getText().toString();
            String pincode = pin.getEditText().getText().toString();
            if(pincode.isEmpty())
                pin.setError("Field cannot be empty");
            else if(pincode.length()<6)
                pin.setError("Pincode must have 6 digits");
            else
                pin.setError(null);
            String citys = city.getEditText().getText().toString();
            if(citys.isEmpty())
                city.setError("Field cannot be empty");
            else
                city.setError(null);
            String text = spinner.getSelectedItem().toString();
            if(text.equalsIgnoreCase("Select your state"))
                ((TextView) spinner.getSelectedView()).setError("");

            if(!(pincode.length()<6||names.isEmpty()||address.isEmpty()||pincode.isEmpty()||citys.isEmpty()||text.equalsIgnoreCase("Select your state"))) {
                editor.putString("Name", names);
                editor.putString("Address", address);
                editor.putString("Landmark", landmark);
                editor.putString("Pincode", pincode);
                editor.putString("City", citys);
                editor.putString("State", text);
                editor.apply();

                Intent intent = new Intent(NameAddActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}