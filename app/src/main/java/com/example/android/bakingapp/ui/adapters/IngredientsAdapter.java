package com.example.android.bakingapp.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.utilities.RecipeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private final Context mContext;
    private List<Ingredient> mIngredients;

    public IngredientsAdapter(@NonNull Context context, @NonNull List<Ingredient> ingredients) {
        this.mContext = context;
        this.mIngredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.ingredient_list_item, viewGroup, false);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {

        // Hold the current ingredient
        Ingredient ingredient = mIngredients.get(position);

        // Get quantity as String
        String quantity = String.valueOf(ingredient.getQuantity());
        String measure = RecipeUtils.setMeasureText(ingredient.getMeasure());
        // Concatenate quantity and measure
        String quantityMeasure = quantity + " " + measure;
        String ingredientName = ingredient.getIngredient();
        // Capitalize the 1st Letter of Ingredient Name
        String ingredientNameCapitalizeFirst = "- " + ingredientName.substring(0, 1).toUpperCase()
                + ingredientName.substring(1);

        // Set the values of the current Ingredient
        holder.quantityMeasureTextView.setText(quantityMeasure);
        holder.ingredientName.setText(ingredientNameCapitalizeFirst);
    }

    @Override
    public int getItemCount() {
        if (null == mIngredients) return 0;
        return mIngredients.size();
    }

    /**
     * When this method is called, we assume we have a completely new
     * set of data, so we call notifyDataSetChanged to tell the RecyclerView to update.
     *
     * @param ingredients is the new data source to update
     */
    public void loadIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_quantity_measure)
        TextView quantityMeasureTextView;
        @BindView(R.id.ingredient_name)
        TextView ingredientName;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
