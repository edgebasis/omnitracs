package com.assesment.omnitrax.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Runner implements Comparable<Runner> {
    @SerializedName(value="Time")
    @Expose
    private int time;
    @SerializedName(value="Age")
    @Expose
    private int age;
    @SerializedName(value="Name")
    @Expose
    private String name;
    private int ranking;

    public Runner() {
    }

    public Runner(int time, int age, String name) {
        this.time = time;
        this.age = age;
        this.name = name;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Runner{"+
                "name: "+ name + '\''+
                "age: "+ age + '\''+
                "time: "+ time;
    }

    @Override
    public int compareTo(Runner runner) {
        return (this.getTime() < runner.getTime() ? -1 :
                this.getTime() == runner.getTime() ? 0:1);
    }
}
