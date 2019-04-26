package com.assesment.omnitrax.network.responses;

import android.util.Log;

import com.assesment.omnitrax.models.Runner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RunnersResponse {
    private static final String TAG = "RunnersResponse";
    @SerializedName(value="Runners")
    @Expose
    private List<Runner> runners;

    @SerializedName(value="Name")
    @Expose
    private String name;

    public List<Runner> getRunners() {
        return this.runners;
    }

    public void setRunners(List<Runner> runners) {

        this.runners = runners;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RunnersResponse{" +
                "count=" + runners.size() +
                ", recipes=" + runners +
                '}';
    }

}
