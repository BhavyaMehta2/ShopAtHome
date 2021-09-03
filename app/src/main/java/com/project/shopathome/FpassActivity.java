package com.project.shopathome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class FpassActivity extends AppCompatActivity {

    FirebaseAuth auth;
    private TextInputLayout mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpass);
        auth = FirebaseAuth.getInstance();

        ExtendedFloatingActionButton reset = findViewById(R.id.rp);
        mail = findViewById(R.id.editText1);
        Button New = findViewById(R.id.Acc);

        New.setOnClickListener(v -> {
            Intent intent = new Intent(FpassActivity.this, MainActivity.class);
            startActivity(intent);
        });

        reset.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mail.getEditText().getText().toString())) {
                mail.setError("Field cannot be empty");
                return;
            }
            auth.sendPasswordResetEmail(mail.getEditText().getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(FpassActivity.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FpassActivity.this, "Mail Sent", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(FpassActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        });
    }
}