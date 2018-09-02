package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Step;
import com.example.android.bakingapp.ui.DetailActivity;
import com.example.android.bakingapp.utilities.RecipeUtils;

import java.util.ArrayList;

public class IngredientsWidgetProvider extends AppWidgetProvider {

    private static final String INGREDIENTS_KEY = "ingredients_key";
    private static final String RECIPE_NAME_KEY = "recipe_name_key";
    private static final String STEPS_LIST_KEY = "steps_list_key";
    private static final String IS_TABLET_KEY = "two_pane";
    private static final String IS_INTENT_FROM_WIDGET = "intent_from_widget";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, ArrayList<Ingredient> ingredients,
                                String recipeName, ArrayList<Step> steps, boolean twoPane, int appWidgetId) {

        // Put the data in an intent
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putParcelableArrayListExtra(INGREDIENTS_KEY, ingredients);
        intent.putParcelableArrayListExtra(STEPS_LIST_KEY, steps);
        intent.putExtra(RECIPE_NAME_KEY, recipeName);
        intent.putExtra(IS_TABLET_KEY, twoPane);
        intent.putExtra(IS_INTENT_FROM_WIDGET, true);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        views.removeAllViews(R.id.ingredients_widget_list_view);
        views.setTextViewText(R.id.widget_recipe_name, recipeName);
        views.setOnClickPendingIntent(R.id.main_widget_layout, pendingIntent);

        for (Ingredient ingredient : ingredients) {
            // Get quantity as String (convert 1st Double to Int)
            String quantity = String.valueOf(ingredient.getQuantity());
            String measure = RecipeUtils.setMeasureText(ingredient.getMeasure());
            // Concatenate quantity and measure
            String quantityMeasure = quantity + " " + measure;
            String ingredientName = ingredient.getIngredient();
            // Capitalize the 1st Letter of Ingredient Name
            String ingredientNameCapitalizeFirst = "- " + ingredientName.substring(0, 1).toUpperCase()
                    + ingredientName.substring(1);

            RemoteViews remoteViewsIngredient = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_list_item);
            remoteViewsIngredient.setTextViewText(R.id.widget_ingredient_name, ingredientNameCapitalizeFirst);
            remoteViewsIngredient.setTextViewText(R.id.widget_ingredient_measure_quantity, quantityMeasure);

            views.addView(R.id.ingredients_widget_list_view, remoteViewsIngredient);
        }

        // Update App widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateIngredientsWidgets(Context context, AppWidgetManager appWidgetManager,
                                                ArrayList<Ingredient> ingredients, String recipeName, ArrayList<Step> steps, boolean twoPane, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, ingredients, recipeName, steps, twoPane, appWidgetId);
        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    @Override
    public void onEnabled(Context context) {
        // Perform any action when an AppWidget for this provider is instantiated
    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }
}
