package com.example.android.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Step;

import java.util.ArrayList;

public class IngredientsService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENTS = "action_update_ingredients";
    private static final String INGREDIENTS_KEY = "ingredients_key";
    private static final String RECIPE_NAME_KEY = "recipe_name_key";
    private static final String STEPS_LIST_KEY = "steps_list_key";
    private static final String IS_TABLET_KEY = "two_pane";

    public IngredientsService() {
        super("IngredientsWidgetService");
    }

    // method to update the Widget App
    public static void startActionUpdateRecipeWidgets(Context context, ArrayList<Ingredient> ingredients, String recipeName, ArrayList<Step> steps, boolean twoPane) {
        Intent intent = new Intent(context, IngredientsService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        intent.putParcelableArrayListExtra(INGREDIENTS_KEY, ingredients);
        intent.putParcelableArrayListExtra(STEPS_LIST_KEY, steps);
        intent.putExtra(RECIPE_NAME_KEY, recipeName);
        intent.putExtra(IS_TABLET_KEY, twoPane);

        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTS.equals(action)) {
                // Get the Ingredients from the intent
                ArrayList<Ingredient> ingredients = intent.getParcelableArrayListExtra(INGREDIENTS_KEY);
                String recipeName = intent.getStringExtra(RECIPE_NAME_KEY);
                ArrayList<Step> steps = intent.getParcelableArrayListExtra(STEPS_LIST_KEY);
                boolean twoPane = intent.getBooleanExtra(IS_TABLET_KEY, false);
                handleActionUpdateIngredients(ingredients, recipeName, steps, twoPane);
            }
        }
    }

    private void handleActionUpdateIngredients(ArrayList<Ingredient> ingredients, String recipeName, ArrayList<Step> steps, boolean twoPane) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
        IngredientsWidgetProvider.updateIngredientsWidgets(this, appWidgetManager, ingredients, recipeName, steps, twoPane, appWidgetIds);
    }
}