package com.project.shopathome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.Viewholder>{
    static int total;
    static double Cost;

    private Context context;
    private ArrayList<Model> courseModelArrayList;
    public AdapterOrder(Context context, ArrayList<Model> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public AdapterOrder.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
        return new AdapterOrder.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrder.Viewholder holder, int position) {
        Model model = courseModelArrayList.get(position);
        holder.Name.setText(model.getName());
        Picasso.get()
                .load(String.valueOf(model.getImage()))
                .into(holder.Image);
        holder.Price.setText("$"+ String.format("%.02f",Double.parseDouble(model.getPrice())));

        SharedPreferences spInstance = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        holder.count = spInstance.getInt(String.valueOf(model.getID()), 0);

        holder.Count.setText("Quantity: "+String.valueOf(holder.count));
    }

    @Override
    public int getItemCount() {
        return courseModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView Image;
        private TextView Name;
        private TextView Price;
        private TextView Count;
        int count;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.image);
            Name = itemView.findViewById(R.id.name);
            Price = itemView.findViewById(R.id.price);
            Count = itemView.findViewById(R.id.quantity);
        }
    }
}
