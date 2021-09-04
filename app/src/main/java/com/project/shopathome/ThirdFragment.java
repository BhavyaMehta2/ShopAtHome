package com.project.shopathome;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThirdFragment extends Fragment {

    View view;
    static RecyclerView recycle;
    static AdapterPOrder adapter;
    static ArrayList<Order> List;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_third, container, false);
        recycle= view.findViewById(R.id.viewt3);

        getItems();
        return view;
    }

    private void getItems() {
        Call<List<Order>> call = RetrofitClientOrder.getInstance().getMyApi().getItems();
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                List<Order> model = response.body();
                List = new ArrayList<>();
                for (int i = 0; i < model.size(); i++) {
                    List.add(new Order(model.get(i).getID(), model.get(i).getDate(), model.get(i).getProducts()));
                }
                adapter = new AdapterPOrder(getActivity(), List);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycle.setLayoutManager(linearLayoutManager);
                recycle.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
}