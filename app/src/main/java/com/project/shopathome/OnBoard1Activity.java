package com.project.shopathome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class OnBoard1Activity extends Fragment {
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.onboard1,container,false);

        ExtendedFloatingActionButton skip = root.findViewById(R.id.skip);

        skip.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            startActivity(intent);
        });
        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isFragmentVisible) {
        super.setUserVisibleHint(isFragmentVisible);
        if (this.isVisible()) {
            if (isFragmentVisible) {
                new Handler().post(() -> {
                    Window window = getActivity().getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.ob, getActivity().getTheme()));
                });
            }
        }
    }
}
