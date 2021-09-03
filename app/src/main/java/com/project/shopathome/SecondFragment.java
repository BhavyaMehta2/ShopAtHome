package com.project.shopathome;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment {

    View view;
    int total;
    static RecyclerView recycle;
    static AdapterCart adapter;
    static ArrayList<Model> List;

    SharedPreferences spInstance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_second, container, false);
        recycle= view.findViewById(R.id.view);

        spInstance = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = spInstance.edit();
        total = spInstance.getInt("Total", 0);

        AdapterCart.Cost=0;

        getItems();

        return view;
    }

    private void getItems() {
        Call<List<Results>> call = RetrofitClient.getInstance().getMyApi().getItems();
        call.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                List<Results> model = response.body();
                List = new ArrayList<>();

                for (int i = 0; i < model.size(); i++) {
                    if(spInstance.getInt(String.valueOf(model.get(i).getID()), 0)!=0) {
                        List.add(new Model(model.get(i).getID(), model.get(i).getName(), model.get(i).getImage(), model.get(i).getPrice(), model.get(i).getCategory(), model.get(i).getDesc(), model.get(i).getRate()));
                        AdapterCart.Cost= AdapterCart.Cost+spInstance.getInt(String.valueOf(model.get(i).getID()), 0)*Double.parseDouble(model.get(i).getPrice());
                        HomeActivity.tot.setText("$"+ String.format("%.02f",AdapterCart.Cost));
                    }
                }

                adapter = new AdapterCart(getActivity(), List);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycle.setLayoutManager(linearLayoutManager);
                recycle.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

}