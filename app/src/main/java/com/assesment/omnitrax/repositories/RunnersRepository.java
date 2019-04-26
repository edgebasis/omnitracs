package com.assesment.omnitrax.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.assesment.omnitrax.models.Runner;
import com.assesment.omnitrax.network.RunnersApiClient;
import com.assesment.omnitrax.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RunnersRepository {
    private static final String TAG = "RunnersRepository";

    private static RunnersRepository instance;
    private RunnersApiClient mRunnersApiClient;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<Runner>> mRunners = new MediatorLiveData<>();

    public static RunnersRepository getInstance() {
        if(instance==null)
            instance = new RunnersRepository();
        return instance;
    }

    public RunnersRepository() {
        mRunnersApiClient = RunnersApiClient.getInstance();
        initMediators();
    }

    private void initMediators() {
        LiveData<List<Runner>> runnersListFromApi = mRunnersApiClient.getRunners();
        mRunners.addSource(runnersListFromApi, new Observer<List<Runner>>() {
            @Override
            public void onChanged(@Nullable List<Runner> runners) {
                Collections.sort(runners);
                setRankings(runners);
                if(runners !=null){
                    mRunners.setValue(runners);
                    doneQuery(runners);
                }else{
                    doneQuery(null);
                }
            }
        });

    }

    private void setRankings(List<Runner> runners) {
        List<Runner> ranking = new ArrayList<>();
        for(int age = 0; age< Constants.AGE_GROUPS.length; age++) {

            for (Runner runner : runners) {
                if(runner.getAge() >= Constants.AGE_GROUPS[age][0] &&
                        runner.getAge()<=Constants.AGE_GROUPS[age][1]) {
                   ranking.add(runner);
                }

            }

            Collections.sort(ranking);
            int i=1;
            for(Runner runner: ranking){
                runner.setRanking(i);
                i++;
            }
            ranking.clear();
        }
    }

    private void doneQuery(List<Runner> runnerList) {
        if(runnerList!=null){
            if(runnerList.size() % 30 != 0){
                mIsQueryExhausted.setValue(true);
            }
        }
        else{
            mIsQueryExhausted.setValue(true);
        }
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mIsQueryExhausted;
    }

    public LiveData<List<Runner>> getRunners(){
        return mRunners;
    }

    public void getRunnersFromApi(){
        mRunnersApiClient.getRunnersFromApi();
    }

    public void cancelRequest(){
        mRunnersApiClient.cancelRequest();
    }

    public LiveData<Boolean> isRunnersRequestTimedOut(){
        return mRunnersApiClient.isRunnersRequestTimedOut();
    }

}
