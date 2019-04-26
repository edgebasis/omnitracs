package com.assesment.omnitrax.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.assesment.omnitrax.AppExecutors;
import com.assesment.omnitrax.models.Runner;
import com.assesment.omnitrax.network.responses.RunnersResponse;
import com.assesment.omnitrax.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class RunnersApiClient {
    private static final String TAG = "RunnersApiClient";

    private static RunnersApiClient instance;
    private MutableLiveData<List<Runner>> mRunners;
    private RetrieveRunnersRunnable mRetrieveRunnersRunnable;
    private MutableLiveData<Boolean> mRunnersRequestTimeout = new MutableLiveData<>();

    public static RunnersApiClient getInstance(){
        if(instance==null){
            instance = new RunnersApiClient();
        }
        return instance;
    }

    private RunnersApiClient(){

        mRunners = new MutableLiveData<>();
    }

    public LiveData<List<Runner>> getRunners(){

        return mRunners;
    }

    public LiveData<Boolean> isRunnersRequestTimedOut(){
        return mRunnersRequestTimeout;
    }

    public void getRunnersFromApi() {
        if(mRetrieveRunnersRunnable!=null) mRetrieveRunnersRunnable=null;
        mRetrieveRunnersRunnable = new RetrieveRunnersRunnable();
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveRunnersRunnable);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                mRunnersRequestTimeout.postValue(true);
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveRunnersRunnable implements Runnable {
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveRunnersRunnable() {
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response response = getRunners().execute();

                if(cancelRequest) return;
                if(response.code() == 200){
                    List<Runner> runnersList = new ArrayList<>(((RunnersResponse)response.body()).getRunners());
                    Log.d(TAG, "run list: " + runnersList.size());
                        mRunners.postValue(runnersList);

                }else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error );
                    mRunners.postValue(null);
                }
            } catch (IOException e){
                e.printStackTrace();
                mRunners.postValue(null);
            }

        }

        private Call<RunnersResponse> getRunners() {
            Log.d(TAG, "getRunners: here");
            return ServiceGenerator.getRunnersApi().getRunners();
        }


        public void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the request");
            cancelRequest = true;
        }
    }

    public void cancelRequest(){
        if(mRetrieveRunnersRunnable != null){
            mRetrieveRunnersRunnable.cancelRequest();
        }
        if(mRetrieveRunnersRunnable != null){
            mRetrieveRunnersRunnable.cancelRequest();
        }
    }

}
