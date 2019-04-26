package com.assesment.omnitrax.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.assesment.omnitrax.R;

public class RunnerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private static final String TAG = "RunnerViewHolder";

    TextView tvRunnersName, tvRunnersAge, tvRunnersTime, tvRunnersRanking;
    OnRunnerListener mOnRunnerListener;

    public RunnerViewHolder(@NonNull View itemView, OnRunnerListener onRunnerListener) {
        super(itemView);
        this.mOnRunnerListener = onRunnerListener;
        tvRunnersName = itemView.findViewById(R.id.tvRunnersName);
        tvRunnersAge = itemView.findViewById(R.id.tvRunnersAge);
        tvRunnersTime = itemView.findViewById(R.id.tvRunnersTime);
        tvRunnersRanking = itemView.findViewById(R.id.tvRunnersRanking);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mOnRunnerListener.onRunnerClick(getAdapterPosition());
    }
}
