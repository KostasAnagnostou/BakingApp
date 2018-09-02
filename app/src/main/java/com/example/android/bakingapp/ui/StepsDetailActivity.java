package com.example.android.bakingapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Step;
import com.example.android.bakingapp.ui.fragments.StepsDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsDetailActivity extends AppCompatActivity {

    private static final String STEP_KEY = "step_key";
    private static final String RECIPE_NAME_KEY = "recipe_name_key";
    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;
    private Step mStep;
    private String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            recipeName = savedInstanceState.getString(RECIPE_NAME_KEY);
        }

        if (savedInstanceState == null) {
            // Get the recipe's data from the Intent
            Intent intent = getIntent();
            mStep = intent.getParcelableExtra(STEP_KEY);
            recipeName = intent.getStringExtra(RECIPE_NAME_KEY);

            // Check if it is Landscape or Portrait
            onConfigurationChanged(getResources().getConfiguration());

            // Create a new Steps Detail Fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
            stepsDetailFragment.setStep(mStep);

            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container, stepsDetailFragment)
                    .commit();
        }

        // Set the Recipe as title in Action Bar & enable Up Button
        setTitle(recipeName);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // This method provides similar behavior like the back button phone
    // when the UP button is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    // When the device rotates show/hide the action-status bar
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if ((newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) && !mStep.getVideoURL().equals("")) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putString(RECIPE_NAME_KEY, recipeName);
    }
}