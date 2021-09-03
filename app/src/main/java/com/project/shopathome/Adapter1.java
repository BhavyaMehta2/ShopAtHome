package com.project.shopathome;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter1 extends RecyclerView.Adapter<Adapter1.Viewholder> {

    private Context context;
    private ArrayList<Model> courseModelArrayList;

    // Constructor
    public Adapter1(Context context, ArrayList<Model> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public Adapter1.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Model model = courseModelArrayList.get(position);
        holder.Name.setText(model.getName());
        Picasso.get()
                .load(String.valueOf(model.getImage()))
                .into(holder.Image);
        holder.Price.setText("$"+ String.format("%.02f",Double.parseDouble(model.getPrice())));
        holder.Rate.setRating((float) model.getRating());
        holder.Count.setText(String.valueOf(model.getCount()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductActivity.class);
                intent.putExtra("id",model.getID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return courseModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView Image;
        private TextView Name;
        private TextView Price;
        private RatingBar Rate;
        private TextView Count;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.image);
            Name = itemView.findViewById(R.id.name);
            Price = itemView.findViewById(R.id.price);
            Rate = itemView.findViewById(R.id.rate);
            Count = itemView.findViewById(R.id.count);

        }
    }
}
