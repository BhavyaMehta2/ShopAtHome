package com.project.shopathome;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class ThirdFragment extends Fragment {

    View view;
    private ExtendedFloatingActionButton reset, delete;
    private TextView mail, phone, whatsapp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_third, container, false);
        reset = view.findViewById(R.id.rp);
        delete = view.findViewById(R.id.delete);
        mail = view.findViewById(R.id.mail);
        phone = view.findViewById(R.id.phone);
        whatsapp = view.findViewById(R.id.whatsapp);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        SharedPreferences prefs = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = prefs.edit();
        SharedPreferences pref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor Editor = pref.edit();

        reset.setOnClickListener(view -> {
            String email=" ";
            assert user != null;
            for (UserInfo profile : user.getProviderData()) {
                email = profile.getEmail();
            }
            assert email != null;
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Mail For Password Reset Sent To Your Registered Email ID", Toast.LENGTH_LONG).show();
                        }
                    });
        });

        delete.setOnClickListener(view -> {
            HomeActivity.tint.setVisibility(View.VISIBLE);
            HomeActivity.confirm.setVisibility(View.VISIBLE);
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blueb, getActivity().getTheme()));
        });

        mail.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String[] recipients= {"f20201846@goa.bitts-pilani.ac.in, bhavyanikhilmehta@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Shop@Home");
            intent.setType("text/html");
            intent.setPackage("com.google.android.gm");
            startActivity(Intent.createChooser(intent, "Send Email"));
        });

        phone.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:9029025782"));
            startActivity(intent);
        });

        whatsapp.setOnClickListener(view -> {
            String url = "https://wa.me/919029025782";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });
        return view;
    }
}