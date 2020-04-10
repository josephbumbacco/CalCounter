package com.example.calcounter.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.calcounter.CustomFoodSearchAdapter;
import com.example.calcounter.Javabean.Food;
import com.example.calcounter.R;
import com.example.calcounter.API.FoodSingleton;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.calcounter.MainActivity.fab;

/**
 * @author Drew Brooks
 *
 * Class handles the search/adding of food objects
 */
public class AddFoodFragment extends Fragment {




    public AddFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);
        /*super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);*/

        fab.hide();

        final EditText searchWord = view.findViewById(R.id.searchWord);
        Button searchButton = view.findViewById(R.id.searchButton);
        final RecyclerView recyclerView = view.findViewById(R.id.searchRecyclerView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<Food> foods = new ArrayList<>();
                String url = "https://api.nutritionix.com/v1_1/search/" + searchWord.getText().toString() + "?" +
                        "fields=item_name%2Citem_id%2Cbrand_name%2Cnf_calories&appId=2eca419b&appKey=4043cad7f22963e024b31b79d0a2d464";


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray array = response.getJSONArray("hits");


                                    for(int i = 0; i < array.length(); i++){
                                        JSONObject object = array.getJSONObject(i);
                                        JSONObject field = object.getJSONObject("fields");

                                        foods.add(new Food(field.getString("item_name"),field.getString("brand_name"),field.getDouble("nf_calories")));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getLocalizedMessage());
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("x-app-id", "2eca419b");
                        params.put("x-app-key", "4043cad7f22963e024b31b79d0a2d464");
                        params.put("x-remote-user-id", "0");
                        return params;
                    }
                };

            FoodSingleton.getInstance(getContext()).getRequestQueue().add(request);

                CustomFoodSearchAdapter adapter = new CustomFoodSearchAdapter(foods, getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            }
        });



        return view;
    }
}

