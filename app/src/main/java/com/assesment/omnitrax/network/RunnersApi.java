package com.assesment.omnitrax.network;

import com.assesment.omnitrax.network.responses.RunnersResponse;


import retrofit2.Call;
import retrofit2.http.GET;

public interface RunnersApi {

    @GET("mobile/runners.json")
    Call<RunnersResponse> getRunners();
}
