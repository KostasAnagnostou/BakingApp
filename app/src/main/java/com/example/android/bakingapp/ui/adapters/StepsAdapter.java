package com.example.android.bakingapp.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private final Context mContext;
    final private StepAdapterOnClickHandler mOnClickHandler;
    private List<Step> mSteps;

    public StepsAdapter(@NonNull Context context, List<Step> steps, StepAdapterOnClickHandler onClickHandler) {
        this.mContext = context;
        this.mSteps = steps;
        this.mOnClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.step_item_list, viewGroup, false);
        view.setFocusable(true);

        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        Step step = mSteps.get(position);
        String stepShortDescription = step.getShortDescription();
        holder.shortDescription.setText(stepShortDescription);
    }

    @Override
    public int getItemCount() {
        if (null == mSteps) return 0;
        return mSteps.size();
    }

    /**
     * When this method is called, we assume we have a completely new
     * set of data, so we call notifyDataSetChanged to tell the RecyclerView to update.
     *
     * @param steps is the new data source to update
     */
    public void loadSteps(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    // Interface to define which Step (item) clicked
    public interface StepAdapterOnClickHandler {
        void onStepClick(Step clickedStep);
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step_short_description)
        TextView shortDescription;

        public StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedStepPosition = getAdapterPosition();
            mOnClickHandler.onStepClick(mSteps.get(clickedStepPosition));
        }
    }
}
