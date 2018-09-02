package com.example.android.bakingapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Step;
import com.example.android.bakingapp.ui.StepsDetailActivity;
import com.example.android.bakingapp.ui.adapters.StepsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment implements StepsAdapter.StepAdapterOnClickHandler {

    private static final String RECIPE_NAME_KEY = "recipe_name_key";
    private static final String STEPS_LIST_KEY = "steps_list_key";
    private static final String STEP_KEY = "step_key";
    private static final String IS_TABLET = "two_pane";
    @BindView(R.id.steps_recycler_view)
    RecyclerView stepsRecyclerView;
    private Context mContext;
    private ArrayList<Step> mSteps;
    private StepsAdapter stepsAdapter;
    private String mRecipeName;
    private Boolean mTwoPane;

    // Mandatory empty constructor
    public StepFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    // Inflates the RecyclerView of all Steps
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);
        ButterKnife.bind(this, rootView);
        mContext = getContext();

        if (savedInstanceState != null) {
            mSteps = savedInstanceState.getParcelableArrayList(STEPS_LIST_KEY);
            mTwoPane = savedInstanceState.getBoolean(IS_TABLET);
            mRecipeName = savedInstanceState.getString(RECIPE_NAME_KEY);
        }

        LinearLayoutManager layoutManagerSteps = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        stepsAdapter = new StepsAdapter(mContext, mSteps, this);
        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setLayoutManager(layoutManagerSteps);
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsRecyclerView.setNestedScrollingEnabled(false);

        return rootView;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.mSteps = steps;
    }

    public void setTwoPane(Boolean twoPane) {
        this.mTwoPane = twoPane;
    }

    public void setRecipeName(String recipeName) {
        this.mRecipeName = recipeName;
    }

    // When a Step clicked open the Steps Detail Activity
    // and pass the Step Data
    @Override
    public void onStepClick(Step clickedStep) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STEP_KEY, clickedStep);
        bundle.putString(RECIPE_NAME_KEY, mRecipeName);
        bundle.putBoolean(IS_TABLET, mTwoPane);
        // When it's not tablet open StepsDetailActivity
        if (!mTwoPane) {
            final Intent intent = new Intent(mContext, StepsDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {// it's tablet, replace StepsDetailFragment
            StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
            stepsDetailFragment.setStep(clickedStep);
            stepsDetailFragment.setTwoPane(mTwoPane);
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.step_detail_container, stepsDetailFragment)
                        .commit();
            }
        }
    }

    // Save the current state of this fragment (steps)
    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(STEPS_LIST_KEY, mSteps);
        currentState.putBoolean(IS_TABLET, mTwoPane);
        currentState.putString(RECIPE_NAME_KEY, mRecipeName);
    }
}
