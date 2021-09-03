package com.project.shopathome;

import android.annotation.SuppressLint;
import android.app.Fragment;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class ViewFragment extends Fragment {

    RecyclerView recycle;
    View view;
    int choice;

    public ViewFragment(int a)
    {
        choice=a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_view, container, false);
        recycle= view.findViewById(R.id.list);
        getItems(choice);
        return view;
    }

    private void getItems(int cat) {
        Call<List<Results>> call = RetrofitClient.getInstance().getMyApi().getItems();
        call.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                List<Results> model = response.body();

                ArrayList<Model> courseModelArrayList = new ArrayList<>();

                if(cat==0)
                    for (int i = 0; i < model.size(); i++) {
                        courseModelArrayList.add(new Model(model.get(i).getID(), model.get(i).getName(), model.get(i).getImage(), model.get(i).getPrice(), model.get(i).getCategory(), model.get(i).getDesc(), model.get(i).getRate()));
                    }

                if(cat==1)
                    for (int i = 0; i < model.size(); i++) {
                        if(model.get(i).getCategory().equalsIgnoreCase("men's clothing"))
                            courseModelArrayList.add(new Model(model.get(i).getID(), model.get(i).getName(), model.get(i).getImage(), model.get(i).getPrice(), model.get(i).getCategory(), model.get(i).getDesc(), model.get(i).getRate()));
                    }
                if(cat==2)
                    for (int i = 0; i < model.size(); i++) {
                        if(model.get(i).getCategory().equalsIgnoreCase("women's clothing"))
                            courseModelArrayList.add(new Model(model.get(i).getID(), model.get(i).getName(), model.get(i).getImage(), model.get(i).getPrice(), model.get(i).getCategory(), model.get(i).getDesc(), model.get(i).getRate()));
                    }
                if(cat==3)
                    for (int i = 0; i < model.size(); i++) {
                        if(model.get(i).getCategory().equalsIgnoreCase("electronics"))
                            courseModelArrayList.add(new Model(model.get(i).getID(), model.get(i).getName(), model.get(i).getImage(), model.get(i).getPrice(), model.get(i).getCategory(), model.get(i).getDesc(), model.get(i).getRate()));
                    }
                if(cat==4)
                    for (int i = 0; i < model.size(); i++) {
                        if(model.get(i).getCategory().equalsIgnoreCase("jewelery"))
                            courseModelArrayList.add(new Model(model.get(i).getID(), model.get(i).getName(), model.get(i).getImage(), model.get(i).getPrice(), model.get(i).getCategory(), model.get(i).getDesc(), model.get(i).getRate()));
                    }
                Adapter1 courseAdapter = new Adapter1(getActivity(), courseModelArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycle.setLayoutManager(linearLayoutManager);
                recycle.setAdapter(courseAdapter);
            }

            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }
}