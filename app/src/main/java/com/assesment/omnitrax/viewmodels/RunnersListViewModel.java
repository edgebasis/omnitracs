package com.assesment.omnitrax.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.assesment.omnitrax.models.Runner;
import com.assesment.omnitrax.repositories.RunnersRepository;

import java.util.List;

public class RunnersListViewModel extends ViewModel {
    private static final String TAG = "RunnersListViewModel";

    private RunnersRepository mRunnersRepository;
    private boolean mIsViewingRunners;


    public RunnersListViewModel() {
        mRunnersRepository = RunnersRepository.getInstance();

    }

    public LiveData<List<Runner>> getRunners(){

        return mRunnersRepository.getRunners();
    }

    public void getRunnersFromApi(){
        mRunnersRepository.getRunnersFromApi();
    }

    public boolean isViewingRunners(){
        return mIsViewingRunners;
    }

    public void setIsViewingRunners(boolean isViewingRunners){
        mIsViewingRunners = isViewingRunners;
    }

    public boolean onBackPressed(){
        if(mIsViewingRunners){
            mIsViewingRunners = false;
            return false;
        }

        return true;
    }
}
