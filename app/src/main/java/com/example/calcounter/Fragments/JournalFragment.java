package com.example.calcounter.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calcounter.CustomFoodAdapter;
import com.example.calcounter.DatabaseHandler;
import com.example.calcounter.Javabean.Food;
import com.example.calcounter.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static com.example.calcounter.MainActivity.fab;

/**
 * A simple {@link Fragment} subclass.
 */
public class JournalFragment extends Fragment {

    public JournalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_journal, container, false);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DatabaseHandler db = new DatabaseHandler(getContext());
        ArrayList<Food> foods = db.getAllFoods();
        CustomFoodAdapter adapter = new CustomFoodAdapter(foods, getContext());
        RecyclerView recyclerView = view.findViewById(R.id.foodsRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db.addFood(new Food("Apple","Gala",200.00));
        return view;
    }
}
