package com.assesment.omnitrax.network;

import android.util.Log;

import com.assesment.omnitrax.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String TAG = "ServiceGenerator";

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RunnersApi runnersApi = retrofit.create(RunnersApi.class);
    public static RunnersApi getRunnersApi(){
        return runnersApi;
    }

}
