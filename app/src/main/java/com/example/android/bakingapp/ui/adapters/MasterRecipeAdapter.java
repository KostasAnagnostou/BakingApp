package com.example.android.bakingapp.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link MasterRecipeAdapter} Renders a list of recipes
 */

public class MasterRecipeAdapter extends RecyclerView.Adapter<MasterRecipeAdapter.RecipeViewHolder> {

    final private RecipeAdapterOnClickHandler mOnClickHandler;
    private final Context mContext;
    private List<Recipe> mRecipes;

    /**
     * Constructor for MasterRecipeAdapter
     */
    public MasterRecipeAdapter(@NonNull Context context, List<Recipe> recipes, RecipeAdapterOnClickHandler onClickHandler) {
        this.mContext = context;
        this.mRecipes = recipes;
        this.mOnClickHandler = onClickHandler;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If our RecyclerView has more than one type of item we
     *                  can use this viewType integer to provide a different layout.
     * @return A new RecipeViewHolder that holds the View for each list item
     */
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_recipe_item, viewGroup, false);
        view.setFocusable(true);

        return new RecipeViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the RecyclerView.
     *
     * @param holder   The ViewHolder to bind data to
     * @param position The position of the data in the RecyclerView
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        // Hold current recipe
        Recipe recipe = mRecipes.get(position);

        String title = recipe.getName();
        String image = recipe.getImage();
        List<Step> steps = recipe.getSteps();
        String stepsNumber = String.valueOf(steps.size());

        holder.recipeTitleTextView.setText(title);
        holder.recipeStepsTextView.setText(stepsNumber);

        if (!image.equals("")) {
            Picasso.with(mContext)
                    .load(image)
                    .placeholder(R.drawable.iccupcake)
                    .into(holder.recipeImageView);
        } else {
            Picasso.with(mContext)
                    .load(R.drawable.iccupcake)
                    .placeholder(R.drawable.iccupcake)
                    .into(holder.recipeImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (null == mRecipes) return 0;
        return mRecipes.size();
    }

    /**
     * When this method is called, we assume we have a completely new
     * set of data, so we call notifyDataSetChanged to tell the RecyclerView to update.
     *
     * @param recipes is the new data source to update
     */
    public void loadRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    // Interface to define which item clicked
    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe clickedRecipe);
    }

    /**
     * Cache of the children views for a list item.
     */
    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_photo)
        ImageView recipeImageView;
        @BindView(R.id.recipe_title)
        TextView recipeTitleTextView;
        @BindView(R.id.recipe_steps)
        TextView recipeStepsTextView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * ImageView and TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         *
         * @param itemView The View that you inflated in
         *                 {@link MasterRecipeAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            // Call setOnClickListener on the View passed into the constructor
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedRecipeIndex = getAdapterPosition();
            Recipe clickedRecipe = mRecipes.get(clickedRecipeIndex);
            mOnClickHandler.onClick(clickedRecipe);
        }
    }
}
