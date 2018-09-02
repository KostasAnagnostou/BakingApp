package com.example.android.bakingapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.example.android.bakingapp.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
public class RecipesRecyclerViewTest {

    private static final String NUTELLA_PIE = "Nutella Pie";
    private static final String BROWNIES_RECIPE = "Brownies";
    private static final String YELLOW_CAKE = "Yellow Cake";
    private static final String CHEESECAKE = "Cheesecake";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    // followed an example from here --> http://blog.sqisland.com/2015/05/espresso-match-toolbar-title.html
    private static ViewInteraction matchToolbarTitle(CharSequence title) {
        return onView(ViewMatchers.isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(title))));
    }

    private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }

    @Test
    public void onClickRecyclerViewItemNutella_OpenDetailActivity() {
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

        // Check if the Toolbar title matches with the Recipe's Name
        matchToolbarTitle(NUTELLA_PIE);
    }

    @Test
    public void onClickRecyclerViewItemBrownies_OpenDetailActivity() {
        // Wait the recipes to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // First, find the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(
                        withText(BROWNIES_RECIPE)), click()));
        // Check if the Toolbar title matches with the Recipe's Name
        matchToolbarTitle(BROWNIES_RECIPE);
    }

    @Test
    public void onClickRecyclerViewItemYellowCake_OpenDetailActivity() {
        // Wait the recipes to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // First, scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(
                        withText(YELLOW_CAKE)), click()));
        // Check if the Toolbar title matches with the Recipe's Name
        matchToolbarTitle(YELLOW_CAKE);
    }

    @Test
    public void onClickRecyclerViewItemCheeseCake_OpenDetailActivity() {
        // Wait the recipes to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // First, find the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(
                        withText(CHEESECAKE)), click()));
        // Check if the Toolbar title matches with the Recipe's Name
        matchToolbarTitle(CHEESECAKE);
    }
}