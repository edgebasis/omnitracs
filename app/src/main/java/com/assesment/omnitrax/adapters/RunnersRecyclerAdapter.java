package com.assesment.omnitrax.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assesment.omnitrax.R;
import com.assesment.omnitrax.models.Runner;
import com.assesment.omnitrax.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.sql.CommonDataSource;

public class RunnersRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "RunnersRecyclerAdapter";

    private static final int EMPTY_VIEW = 0;
    private static final int NORMAL_VIEW = 1;
    private static final int LOADING_VIEW = 2;


    private List<Runner> mRunners;
    private OnRunnerListener mOnRunnerListener;

    public RunnersRecyclerAdapter(OnRunnerListener mOnRunnerListener) {
        this.mOnRunnerListener = mOnRunnerListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = null;
        switch(viewType){
            case NORMAL_VIEW:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_runners_list_item, viewGroup, false);
                return new RunnerViewHolder(view, mOnRunnerListener);
            case LOADING_VIEW:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading_list_item, viewGroup, false);
                return new RunnerViewHolder(view, mOnRunnerListener);
            case EMPTY_VIEW:
                default:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_empty_list_item, viewGroup, false);
                return new RunnerViewHolder(view, mOnRunnerListener);


        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType==NORMAL_VIEW){
            ((RunnerViewHolder)viewHolder).tvRunnersName.setText(mRunners.get(position).getName());
            ((RunnerViewHolder)viewHolder).tvRunnersTime.setText(String.valueOf(mRunners.get(position).getTime()));
            ((RunnerViewHolder)viewHolder).tvRunnersAge.setText(String.valueOf(mRunners.get(position).getAge()));
            ((RunnerViewHolder)viewHolder).tvRunnersRanking.setText(String.valueOf(mRunners.get(position).getRanking()));

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mRunners.get(position).getName().equals(Constants.LOADING_TEXT))
            return LOADING_VIEW;
        else if(mRunners.get(position).getName().equals(Constants.EMPTY_TEXT))
            return EMPTY_VIEW;
        else
            return NORMAL_VIEW;

    }

    public void hideLoading(){
        if (isLoading()) {
            for(Runner runner : mRunners){
                if(runner.getName().equals(Constants.LOADING_TEXT)){
                    mRunners.remove(runner);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void displayLoading(){
        if(!isLoading()){
            Runner runner = new Runner();
            runner.setName(Constants.LOADING_TEXT);
            List<Runner> runnersLoadingList = new ArrayList<>();
            runnersLoadingList.add(runner);
            mRunners = runnersLoadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if(mRunners!=null){
            if(mRunners.size()>0){
                if(mRunners.get(mRunners.size()-1).getName().equals(Constants.LOADING_TEXT))
                    return true;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return mRunners != null ? mRunners.size() : 0;
    }

    public void setRunners(List<Runner> runners){
        mRunners = runners;
        notifyDataSetChanged();
    }

    public Runner getSelectedRunner(int position){
        return mRunners != null  && mRunners.size() > 0 ? mRunners.get(position): null;
    }
}
