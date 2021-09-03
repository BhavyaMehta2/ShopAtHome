package com.project.shopathome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    private TextInputLayout user, pass;
    TextView fp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, NameAddActivity.class));
            finish();
        }
            setContentView(R.layout.activity_main);
            Button New = findViewById(R.id.Acc);
            ExtendedFloatingActionButton login = findViewById(R.id.Login);
            user = findViewById(R.id.editText1);
            pass = findViewById(R.id.editText2);
            fp = findViewById(R.id.fpass);
            auth = FirebaseAuth.getInstance();

            fp.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, FpassActivity.class);
                startActivity(intent);
            });

            New.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            });

            login.setOnClickListener(v -> {
                String Username = user.getEditText().getText().toString();
                String Password = pass.getEditText().getText().toString();
                if (TextUtils.isEmpty(Username)) {
                    user.setError("Field cannot be empty");
                } else
                    user.setError(null);
                if (TextUtils.isEmpty(Password)) {
                    pass.setError("Field cannot be empty");
                } else
                    pass.setError(null);
                if (TextUtils.isEmpty(Username) || TextUtils.isEmpty(Password))
                    return;
                auth.signInWithEmailAndPassword(Username, Password)
                        .addOnCompleteListener(MainActivity.this, task -> {
                            if (!task.isSuccessful()) {
                                try {
                                    throw task.getException();
                                } catch(FirebaseAuthWeakPasswordException e) {
                                   pass.setError("Weak Password");
                                    pass.requestFocus();
                                } catch(FirebaseAuthInvalidCredentialsException e) {
                                    user.setError("Invalid email");
                                    user.requestFocus();
                                } catch(FirebaseAuthUserCollisionException e) {
                                    user.setError("Check");
                                    user.requestFocus();
                                } catch(Exception e) {
                                    Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, NameAddActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
            });
        }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}