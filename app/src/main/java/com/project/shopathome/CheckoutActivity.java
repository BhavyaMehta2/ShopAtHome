package com.project.shopathome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class CheckoutActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView top;
    private ExtendedFloatingActionButton con;
    private TextInputLayout name, add, land, pin, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        spinner = findViewById(R.id.spinner1);
        con= findViewById(R.id.con);
        name = findViewById(R.id.editText1);
        add = findViewById(R.id.editText2);
        land = findViewById(R.id.editText3);
        pin = findViewById(R.id.editText4);
        city = findViewById(R.id.editText5);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();

        name.getEditText().setText(prefs.getString("Name", null));
        add.getEditText().setText(prefs.getString("Address", null));
        land.getEditText().setText(prefs.getString("Landmark", null));
        pin.getEditText().setText(prefs.getString("Pincode", null));
        city.getEditText().setText(prefs.getString("City", null));

        String compareValue = prefs.getString("State", null);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.state, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            spinner.setSelection(spinnerPosition);
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

            if(!(names.isEmpty()||address.isEmpty()||pincode.isEmpty()||pincode.length()<6||citys.isEmpty()||text.equalsIgnoreCase("Select your state"))) {
                editor.putString("Name", names);
                editor.putString("Address", address);
                editor.putString("Landmark", landmark);
                editor.putString("Pincode", pincode);
                editor.putString("City", citys);
                editor.putString("State", text);
                editor.apply();

                Intent intent = new Intent(CheckoutActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}