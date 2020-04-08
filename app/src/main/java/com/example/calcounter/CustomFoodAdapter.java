package com.example.calcounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.calcounter.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calcounter.Javabean.Food;

import java.util.ArrayList;

public class CustomFoodAdapter extends RecyclerView.Adapter<CustomFoodAdapter.CustomViewHolder> {

    private ArrayList<Food> foods;
    private Context context;

    public CustomFoodAdapter(ArrayList<Food> foods, Context context) {
        this.foods = foods;
        this.context = context;
    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_view,parent,false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        final Food food = foods.get(position);
        holder.foodName.setText(food.getName());
        holder.foodBrand.setText(food.getBrand());
        holder.foodCalories.setText(food.getCalories() + "");
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        protected TextView foodName;
        protected TextView foodBrand;
        protected TextView foodCalories;
        protected ImageView addFood;

        public CustomViewHolder(final View view) {
            super(view);
            this.foodName = view.findViewById(R.id.foodName);
            this.foodBrand = view.findViewById(R.id.foodBrand);
            this.foodCalories = view.findViewById(R.id.foodCalories);
            this.addFood = view.findViewById(R.id.addFood);

            addFood.setVisibility(View.GONE);

            //Method here to manually update calories
            //
            //
            //
            //

            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete " + foods.get(
                            getLayoutPosition()).getName() + " from your journal?")
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseHandler db = new DatabaseHandler(context);
                            db.deleteFood(foods.get(
                                    getLayoutPosition()).getId());
                            foods.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            db.close();
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();

            return false;
        }
    }


}
