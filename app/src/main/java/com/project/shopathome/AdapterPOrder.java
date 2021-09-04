package com.project.shopathome;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterPOrder extends RecyclerView.Adapter<AdapterPOrder.Viewholder> {
    static double Cost;

    private Context context;
    private ArrayList<Order> courseModelArrayList;
    public AdapterPOrder(Context context, ArrayList<Order> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public AdapterPOrder.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
        return new AdapterPOrder.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPOrder.Viewholder holder, int position) {
        Order model = courseModelArrayList.get(position);
        int pid = model.getID();
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
        private TextView Date;
        int count;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.image);
            Name = itemView.findViewById(R.id.name);
            Price = itemView.findViewById(R.id.price);
            Count = itemView.findViewById(R.id.quantity);
            Date = itemView.findViewById(R.id.date);
        }
    }
}

