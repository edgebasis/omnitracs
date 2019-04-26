package com.assesment.omnitrax;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.assesment.omnitrax.adapters.OnRunnerListener;
import com.assesment.omnitrax.adapters.RunnersRecyclerAdapter;
import com.assesment.omnitrax.models.Runner;
import com.assesment.omnitrax.util.VerticalSpacingItemDecorator;
import com.assesment.omnitrax.viewmodels.RunnersListViewModel;

import java.util.List;

public class RunnersActivity extends BaseActivity implements OnRunnerListener {

    private static final String TAG = "RunnersActivity";

    private RunnersListViewModel mRunnersListViewModel;
    private RecyclerView mRecyclerView;
    private RunnersRecyclerAdapter mRunnersRecyclerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runners);

        mRecyclerView = findViewById(R.id.rvRunnersList);
        mRunnersListViewModel = ViewModelProviders.of(this).get(RunnersListViewModel.class);
        initRecyclerView();
        subscribeObservers();
        loadRunners();
        showProgressBar(false);

    }

    private void loadRunners() {
        mRunnersRecyclerAdapter.displayLoading();
        mRunnersListViewModel.getRunnersFromApi();
    }

    private void subscribeObservers() {
        mRunnersListViewModel.getRunners().observe(this, new Observer<List<Runner>>() {

            @Override
            public void onChanged(@Nullable List<Runner> runnerList) {
                if(runnerList!=null){
                        mRunnersRecyclerAdapter.setRunners(runnerList);
                }
            }
        });

    }

    private void initRecyclerView() {
        mRunnersListViewModel.setIsViewingRunners(true);

        mRunnersRecyclerAdapter = new RunnersRecyclerAdapter(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mRunnersRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onRunnerClick(int position) {
        Toast.makeText(this, "Clicked on runner " + mRunnersRecyclerAdapter.getSelectedRunner(position).getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        if(mRunnersListViewModel.onBackPressed()){
            super.onBackPressed();
        }

    }
}
