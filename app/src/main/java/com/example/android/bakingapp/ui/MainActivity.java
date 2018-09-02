package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.ui.fragments.MasterListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // if is not null set mTwoPane to true
        mTwoPane = findViewById(R.id.tablet_container) != null;

        if (savedInstanceState == null) {
            MasterListFragment masterListFragment = new MasterListFragment();
            masterListFragment.setTwoPane(mTwoPane);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.master_list_fragment_container, masterListFragment)
                    .commit();
        }

        setSupportActionBar(mToolbar);
    }
}
