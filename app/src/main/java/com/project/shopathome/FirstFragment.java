package com.project.shopathome;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {

    private TextView tw1;
    private RecyclerView recycle, recycle1, recycle2, recycle3, recycle4, recycle5;

    View view;
    TextView firstButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        tw1 = view.findViewById(R.id.tw1);
        recycle= view.findViewById(R.id.view);
        recycle1= view.findViewById(R.id.view1);
        recycle2= view.findViewById(R.id.view2);
        recycle3= view.findViewById(R.id.view3);
        recycle4= view.findViewById(R.id.view4);
        recycle5= view.findViewById(R.id.view5);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycle1);
        recycle1.addItemDecoration(new DotsIndicatorDecoration(10, 10 * 4, 30, getResources().getColor(R.color.blue), getResources().getColor(R.color.blue)));

        tw1.setOnClickListener(v -> {
            loadFragment(new ViewFragment(0));
            HomeActivity.all.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
        });

        getItems();

        return view;
    }

    private void getItems() {
        Call<List<Results>> call = RetrofitClient.getInstance().getMyApi().getItems();
        call.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                List<Results> model = response.body();

                ArrayList<Model> List = new ArrayList<>();
                ArrayList<Model> List1 = new ArrayList<>();
                ArrayList<Model> List2 = new ArrayList<>();
                ArrayList<Model> List3 = new ArrayList<>();
                ArrayList<Model> List4 = new ArrayList<>();
                ArrayList<Model> List5 = new ArrayList<>();

                int[] a={13,4,12,11};
                int[] k={0,0,0,0};
                int[] choice= new int[20];
                for(int i=0; i<20; i++)
                    choice[i]=0;

                for (int i = 0; i < model.size(); i++) {
                    if(i%4==0) {
                        int r=0;
                        while(choice[r]!=0) {
                            r = (int) (Math.random() * 20);
                        }
                        choice[r]=1;
                        List.add(new Model(model.get(r).getID(), model.get(r).getName(), model.get(r).getImage(), model.get(r).getPrice(), model.get(r).getCategory(), model.get(r).getDesc(), model.get(i).getRate()));
                    }

                    if(i%5==0)
                        List1.add(new Model(model.get(a[i/5]).getID(), model.get(a[i/5]).getName(), model.get(a[i/5]).getImage(), model.get(a[i/5]).getPrice(), model.get(a[i/5]).getCategory(), model.get(a[i/5]).getDesc(), model.get(i).getRate()));
                    if(model.get(i).getCategory().equalsIgnoreCase("electronics")&&k[0]<3)
                    {
                        k[0]++;
                        List2.add(new Model(model.get(i).getID(), model.get(i).getName(), model.get(i).getImage(), model.get(i).getPrice(), model.get(i).getCategory(), model.get(i).getDesc(), model.get(i).getRate()));
                    }
                    if(model.get(i).getCategory().equalsIgnoreCase("jewelery")&&k[1]<3)
                    {
                        k[1]++;
                        List3.add(new Model(model.get(i).getID(), model.get(i).getName(), model.get(i).getImage(), model.get(i).getPrice(), model.get(i).getCategory(), model.get(i).getDesc(), model.get(i).getRate()));
                    }
                    if(model.get(i).getCategory().equalsIgnoreCase("men's clothing")&&k[2]<3)
                    {
                        k[2]++;
                        List4.add(new Model(model.get(i).getID(), model.get(i).getName(), model.get(i).getImage(), model.get(i).getPrice(), model.get(i).getCategory(), model.get(i).getDesc(), model.get(i).getRate()));
                    }
                    if(model.get(i).getCategory().equalsIgnoreCase("women's clothing")&&k[3]<3)
                    {
                        k[3]++;
                        List5.add(new Model(model.get(i).getID(), model.get(i).getName(), model.get(i).getImage(), model.get(i).getPrice(), model.get(i).getCategory(), model.get(i).getDesc(), model.get(i).getRate()));
                    }
                }

                Adapter adapter = new Adapter(getActivity(), List);
                Adapter1 adapter1 = new Adapter1(getActivity(), List1);
                Adapter1 adapter2 = new Adapter1(getActivity(), List2);
                Adapter1 adapter3 = new Adapter1(getActivity(), List3);
                Adapter1 adapter4 = new Adapter1(getActivity(), List4);
                Adapter1 adapter5 = new Adapter1(getActivity(), List5);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                LinearLayoutManager linearLayoutManager5 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycle.setLayoutManager(linearLayoutManager);
                recycle1.setLayoutManager(linearLayoutManager1);
                recycle2.setLayoutManager(linearLayoutManager2);
                recycle3.setLayoutManager(linearLayoutManager3);
                recycle4.setLayoutManager(linearLayoutManager4);
                recycle5.setLayoutManager(linearLayoutManager5);
                recycle.setAdapter(adapter);
                recycle1.setAdapter(adapter1);
                recycle2.setAdapter(adapter2);
                recycle3.setAdapter(adapter3);
                recycle4.setAdapter(adapter4);
                recycle5.setAdapter(adapter5);
                recycle2.setNestedScrollingEnabled(false);
                recycle3.setNestedScrollingEnabled(false);
                recycle4.setNestedScrollingEnabled(false);
                recycle5.setNestedScrollingEnabled(false);

            }
            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}