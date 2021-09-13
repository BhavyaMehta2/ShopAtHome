package com.project.shopathome;

import android.annotation.SuppressLint;
import android.app.Fragment;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class ViewFragment extends Fragment {

    RecyclerView recycle;
    TextView tw;
    LottieAnimationView anim;
    View view;
    String[] query;
    int choice;

    public ViewFragment(int a, String[] b)
    {
        choice=a;
        query=b;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_view, container, false);
        recycle= view.findViewById(R.id.list);
        anim= view.findViewById(R.id.animationView);
        tw = view.findViewById(R.id.tw);
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
                if(cat==5)
                    for (int i = 0; i < model.size(); i++) {
                        String[] check= (model.get(i).getName()+" "+model.get(i).getCategory()+" "+model.get(i).getDesc()).toUpperCase().split(" |'");
                        List<String> nameList = new ArrayList<>(Arrays.asList(check));
                        for(int j = 0; j<query.length; j++)
                            if(nameList.contains(query[j]))
                                courseModelArrayList.add(new Model(model.get(i).getID(), model.get(i).getName(), model.get(i).getImage(), model.get(i).getPrice(), model.get(i).getCategory(), model.get(i).getDesc(), model.get(i).getRate()));
                    }
                if(courseModelArrayList.size()==0) {
                    tw.setText("No Search Results");
                    anim.setVisibility(View.VISIBLE);
                }
                else
                {
                    tw.setText("");
                    anim.setVisibility(View.GONE);
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