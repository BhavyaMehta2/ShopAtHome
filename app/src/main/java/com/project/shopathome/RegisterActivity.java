package com.project.shopathome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout user, pass, cpass;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        ExtendedFloatingActionButton reg = findViewById(R.id.reg);
        Button login = findViewById(R.id.Acc);
        user = findViewById(R.id.editText1);
        pass = findViewById(R.id.editText2);
        cpass = findViewById(R.id.editText3);

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });

        reg.setOnClickListener(v -> {
            String Username = user.getEditText().getText().toString();
            String Password = pass.getEditText().getText().toString();
            String CPassword = cpass.getEditText().getText().toString();
            if (TextUtils.isEmpty(Username)) {
                user.setError("Field cannot be empty");
            } else
                user.setError(null);
            if (TextUtils.isEmpty(Password)) {
                pass.setError("Field cannot be empty");
            } else
                pass.setError(null);
            if (TextUtils.isEmpty(Password)) {
                cpass.setError("Field cannot be empty");
            } else
                cpass.setError(null);
            if (!CPassword.equals(Password)) {
                pass.setError("Password doesn't match");
                cpass.setError("Re-enter your password here");
            }
            if (TextUtils.isEmpty(Username) || TextUtils.isEmpty(Password) || !CPassword.equals(Password))
                return;
            auth.createUserWithEmailAndPassword(Username, Password)
                    .addOnCompleteListener(RegisterActivity.this, task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, NameAddActivity.class));
                            finish();
                        }
                    });
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}