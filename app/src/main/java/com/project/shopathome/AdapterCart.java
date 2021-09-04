package com.project.shopathome;

import android.app.backup.SharedPreferencesBackupHelper;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.Viewholder> {

    static int total;
    static double Cost;

    private Context context;
    private ArrayList<Model> courseModelArrayList;

    // Constructor
    public AdapterCart(Context context, ArrayList<Model> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
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

        SharedPreferences spInstance = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = spInstance.edit();
        holder.count = spInstance.getInt(String.valueOf(model.getID()), 0);
        total = spInstance.getInt("Total", 0);

        if(holder.count==1)
        {
            holder.minus.setVisibility(View.GONE);
            holder.del.setVisibility(View.VISIBLE);
        }

        holder.Count.setText(String.valueOf(holder.count));


        holder.plus.setOnClickListener(v -> {
            spEditor.putInt(String.valueOf(model.getID()), holder.count+1);
            spEditor.putInt("Total", total+1);
            spEditor.apply();
            holder.count = spInstance.getInt(String.valueOf(model.getID()), 0);
            total = spInstance.getInt("Total", 0);
            holder.Count.setText(String.valueOf(holder.count));
            HomeActivity.twc.setText(String.valueOf(total));

            Cost= Cost+Double.parseDouble(model.getPrice());
            HomeActivity.tot.setText("$"+ String.format("%.02f",Cost));

            if(holder.count>1)
            {
                holder.minus.setVisibility(View.VISIBLE);
                holder.del.setVisibility(View.GONE);
            }
            HomeActivity.anim1.playAnimation();
            HomeActivity.anim1.setMinFrame(20);
            HomeActivity.anim1.setMaxFrame(28);

            final Handler handler = new Handler();
            handler.postDelayed(() -> HomeActivity.anim1.pauseAnimation(), 500);
        });

        holder.minus.setOnClickListener(v -> {
            spEditor.putInt(String.valueOf(model.getID()), holder.count-1);
            spEditor.putInt("Total", total-1);
            spEditor.apply();
            holder.count = spInstance.getInt(String.valueOf(model.getID()), 0);
            total = spInstance.getInt("Total", 0);
            holder.Count.setText(String.valueOf(holder.count));
            HomeActivity.twc.setText(String.valueOf(total));

            Cost= Cost-Double.parseDouble(model.getPrice());
            HomeActivity.tot.setText("$"+ String.format("%.02f",Cost));

            if(holder.count==1)
            {
                holder.minus.setVisibility(View.GONE);
                holder.del.setVisibility(View.VISIBLE);
            }
        });

        holder.del.setOnClickListener(v -> {
            Cost= Cost-Double.parseDouble(model.getPrice());
            HomeActivity.tot.setText("$"+ String.format("%.02f",Cost));

            spEditor.putInt(String.valueOf(model.getID()), --holder.count);
            spEditor.putInt("Total", --total);
            spEditor.apply();
            holder.count = spInstance.getInt(String.valueOf(model.getID()), 0);
            total = spInstance.getInt("Total", 0);
            holder.Count.setText(String.valueOf(holder.count));
            HomeActivity.twc.setText(String.valueOf(total));

            SecondFragment.List.remove(position);
            SecondFragment.recycle.removeViewAt(position);
            SecondFragment.adapter.notifyItemRemoved(position);
            SecondFragment.adapter.notifyItemRangeChanged(position, SecondFragment.List.size());
            if(SecondFragment.List.size()==0)
            {
                SecondFragment.anim.setVisibility(View.VISIBLE);
            }
        });

        holder.del1.setOnClickListener(v -> {
            spEditor.putInt(String.valueOf(model.getID()), 0);
            spEditor.putInt("Total", total-holder.count);
            spEditor.apply();

            Cost= Cost-holder.count*Double.parseDouble(model.getPrice());
            HomeActivity.tot.setText("$"+ String.format("%.02f",Cost));

            holder.count = spInstance.getInt(String.valueOf(model.getID()), 0);
            total = spInstance.getInt("Total", 0);
            holder.Count.setText(String.valueOf(holder.count));
            HomeActivity.twc.setText(String.valueOf(total));

            SecondFragment.List.remove(position);
            SecondFragment.recycle.removeViewAt(position);
            SecondFragment.adapter.notifyItemRemoved(position);
            SecondFragment.adapter.notifyItemRangeChanged(position, SecondFragment.List.size());
            if(SecondFragment.List.size()==0)
            {
                SecondFragment.anim.setVisibility(View.VISIBLE);
            }
        });

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
        return courseModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView Image;
        private ImageView minus;
        private ImageView plus;
        private ImageView del;
        private TextView Name;
        private TextView Price;
        private TextView Count;
        private ExtendedFloatingActionButton del1;
        int count;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            del1 = itemView.findViewById(R.id.del1);
            Image = itemView.findViewById(R.id.image);
            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);
            del = itemView.findViewById(R.id.del);
            Name = itemView.findViewById(R.id.name);
            Price = itemView.findViewById(R.id.price);
            Count = itemView.findViewById(R.id.count);
        }
    }
}