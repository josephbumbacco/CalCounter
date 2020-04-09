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

/**
 * @author Drew Brooks
 *
 * Adapter Class that handles the functionality for each food object
 */
public class CustomFoodAdapter extends RecyclerView.Adapter<CustomFoodAdapter.CustomViewHolder> {

    private ArrayList<Food> foods;
    private Context context;

    public CustomFoodAdapter(ArrayList<Food> foods, Context context) {
        this.foods = foods;
        this.context = context;
    }


    /**
     * Method handles inflating the view, and returning the view inside a custom view holder
     *
     * @param parent
     * @param viewType
     * @return new CustomViewHolder for the current view
     */
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

    /**
     *
     * @return size of foods array
     */
    @Override
    public int getItemCount() {
        return foods.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        protected TextView foodName;
        protected TextView foodBrand;
        protected TextView foodCalories;
        protected ImageView addFood;

        /**
         * Code that places view over food object, allows functionality on object, and adds clicklisteners to components in the object.
         *
         * @param view
         */
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
            view.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }

        /**
         * Code handles a long click on a food object, allows user to delete clicked object
         *
         * @param v
         * @return runs delete method if user selects okay, returns false if no
         */
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
