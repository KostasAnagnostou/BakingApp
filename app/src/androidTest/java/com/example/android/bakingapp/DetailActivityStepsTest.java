package com.example.android.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DetailActivityStepsTest {

    private static final String NUTELLA_PIE = "Nutella Pie";
    private static final String NUTELLA_PIE_STEP = "Press the crust into baking form.";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onClickRecipe_OpenDetailActivity_CheckStep() {
        // Wait the recipes to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // First, find the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(
                        withText(NUTELLA_PIE)), click()));

        onView(ViewMatchers.withId(R.id.steps_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, scrollTo()));

        onView(withText(NUTELLA_PIE_STEP)).perform(click());

        onView(withId(R.id.step_detail_short_description))
                .check(matches(withText(NUTELLA_PIE_STEP)));
    }
}
