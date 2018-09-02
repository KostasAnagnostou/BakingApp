package com.example.android.bakingapp.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.ui.adapters.IngredientsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientFragment extends Fragment {

    private static final String INGREDIENTS_KEY = "ingredients_key";
    @BindView(R.id.ingredient_recycler_view)
    RecyclerView mRecyclerView;
    private Context mContext;
    private ArrayList<Ingredient> mIngredients;
    private IngredientsAdapter mIngredientAdapter;

    // Mandatory empty constructor
    public IngredientFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    // Inflates the RecyclerView of all Recipes
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, rootView);
        mContext = getContext();

        if (savedInstanceState != null) {
            mIngredients = savedInstanceState.getParcelableArrayList(INGREDIENTS_KEY);
        }

        LinearLayoutManager layoutManagerIngredients = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mIngredientAdapter = new IngredientsAdapter(mContext, mIngredients);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManagerIngredients);
        mRecyclerView.setAdapter(mIngredientAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        return rootView;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.mIngredients = ingredients;
    }

    // Save the current state of this fragment (the ingredients)
    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(INGREDIENTS_KEY, mIngredients);
    }
}
