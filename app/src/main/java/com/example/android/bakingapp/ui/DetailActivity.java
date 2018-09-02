package com.example.android.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Step;
import com.example.android.bakingapp.ui.fragments.IngredientFragment;
import com.example.android.bakingapp.ui.fragments.StepFragment;
import com.example.android.bakingapp.ui.fragments.StepsDetailFragment;
import com.example.android.bakingapp.widget.IngredientsService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String RECIPE_NAME_KEY = "recipe_name_key";
    private static final String INGREDIENTS_KEY = "ingredients_key";
    private static final String STEPS_LIST_KEY = "steps_list_key";
    private static final String NESTED_SCROLL_POSITION = "nested_scroll_position";
    private static final String IS_TABLET_KEY = "two_pane";
    private static final String IS_INTENT_FROM_WIDGET = "intent_from_widget";
    @BindView(R.id.nested_detail_scroll_view)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mSteps;
    private String recipeName;
    private boolean mTwoPane;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {
            // Get the recipe's data from the Intent
            Intent intent = getIntent();
            recipeName = intent.getStringExtra(RECIPE_NAME_KEY);
            mIngredients = intent.getParcelableArrayListExtra(INGREDIENTS_KEY);
            mSteps = intent.getParcelableArrayListExtra(STEPS_LIST_KEY);
            mTwoPane = intent.getBooleanExtra(IS_TABLET_KEY, false);

            // Create a new Ingredient Fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            IngredientFragment ingredientFragment = new IngredientFragment();
            ingredientFragment.setIngredients(mIngredients);

            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_container, ingredientFragment)
                    .commit();

            // Create a new Step Fragment
            StepFragment stepFragment = new StepFragment();
            stepFragment.setSteps(mSteps);
            stepFragment.setRecipeName(recipeName);
            stepFragment.setTwoPane(mTwoPane);
            fragmentManager.beginTransaction()
                    .add(R.id.steps_container, stepFragment)
                    .commit();
            // Initialize the StepsDetailFragment if it's tablet
            if (mTwoPane) {
                StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
                stepsDetailFragment.setStep(mSteps.get(0));
                stepsDetailFragment.setTwoPane(mTwoPane);
                fragmentManager.beginTransaction()
                        .add(R.id.step_detail_container, stepsDetailFragment)
                        .commit();
            }

        } else {// restore the saved values
            recipeName = savedInstanceState.getString(RECIPE_NAME_KEY);
            // Those values (twoPane, Ingredients & Steps) used here
            // (inside this activity) when the intent is from the widget
            mIngredients = savedInstanceState.getParcelableArrayList(INGREDIENTS_KEY);
            mSteps = savedInstanceState.getParcelableArrayList(STEPS_LIST_KEY);
            mTwoPane = savedInstanceState.getBoolean(IS_TABLET_KEY);
        }

        setTitle(recipeName);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Save Recipe's name and Nested ScrollView
    // which they live in the Detail Activity
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RECIPE_NAME_KEY, recipeName);
        // Those values (twoPane, Ingredients & Steps) used when the intent is from the widget
        outState.putBoolean(IS_TABLET_KEY, mTwoPane);
        outState.putParcelableArrayList(INGREDIENTS_KEY, mIngredients);
        outState.putParcelableArrayList(STEPS_LIST_KEY, mSteps);
        outState.putIntArray(NESTED_SCROLL_POSITION,
                new int[]{mNestedScrollView.getScrollX(), mNestedScrollView.getScrollY()});
    }

    // For restoring the nested Scroll Position
    // when the phone rotates i used an example
    // from here ---> https://stackoverflow.com/questions/29208086/save-the-position-of-scrollview-when-the-orientation-changes
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray(NESTED_SCROLL_POSITION);
        if (position != null)
            mNestedScrollView.post(new Runnable() {
                public void run() {
                    mNestedScrollView.scrollTo(position[0], position[1]);
                }
            });
    }

    /**
     * Methods for setting up the menu
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu, menu);
        return true;
    }

    // This method provides similar behavior like the back button phone
    // when the UP button is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean isIntentFromAppWidget = getIntent().getBooleanExtra(IS_INTENT_FROM_WIDGET, false);
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isIntentFromAppWidget) {// start the MainActivity
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    onBackPressed();
                }
                return true;
            case R.id.action_add_widget:
                // Start Updating the Ingredient's App widget
                IngredientsService.startActionUpdateRecipeWidgets(this, mIngredients, recipeName, mSteps, mTwoPane);
                Toast.makeText(this, R.string.widget_created, Toast.LENGTH_SHORT).show();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }
}
